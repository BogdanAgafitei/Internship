package com.seedon.SeedOnTanda.user.service;


import com.seedon.SeedOnTanda.common.Encryption;
import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.jwt.repository.JwtRepository;
import com.seedon.SeedOnTanda.kafka.KafkaNotify;
import com.seedon.SeedOnTanda.role.service.RoleService;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.entity.User;
import com.seedon.SeedOnTanda.user.repository.UserRepository;
import com.seedon.SeedOnTanda.user.state.AdminState;
import com.seedon.SeedOnTanda.user.state.UserState;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final JwtRepository jwtRepository;
    private final KafkaNotify kafkaNotify;

    public UserDTO getUserById(String id) {
        return findByIdOrNotFound(id, Encryption::userToDtoMapper);
    }

    public Page<UserDTO> getUsers(int page, int size, String[] sort_by, String direction) {
        final var pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), sort_by)
        );
        return userRepository.findAll(pageRequest)
                .map(Encryption::userToDtoMapper);
    }

    public UserDTO saveUser(UserDTO userDto) {
        validateUser(userDto);
        logger.info(">>>> Saving an user to database...");
        var roleList = roleService.getRolesByName(userDto.getRoles());
        final var userTemp = Encryption.userDtoToUserMapper(userDto);
        roleList.forEach(userTemp::addRole);
        userTemp.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRoles().contains(RoleValues.ROLE_ADMIN)) {
            userTemp.setState(new AdminState());
        } else {
            userTemp.setState(new UserState());
        }
        kafkaNotify.notifyUser("User has been saved");
        return Encryption.userToDtoMapper(userRepository.save(userTemp));
    }

    public UserDTO updateUser(String id, UserDTO userDto) {
        validateUserBeforeUpdate(userDto, id);
        logger.info("User trying to be updated with id : " + id);
        final var user = findByIdOrNotFound(id);
        final var updated = Encryption.userDtoToUserMapper(userDto);
        var roles = roleService.getRolesByName(userDto.getRoles());
        final var password = passwordEncoder.encode(userDto.getPassword());
        user.updateStates(updated, roles, password);
        if (userDto.getRoles().contains(RoleValues.ROLE_ADMIN)) {
            user.setState(new AdminState());
        } else {
            user.setState(new UserState());
        }
        return Encryption.userToDtoMapper(userRepository.save(user));
    }

    @Transactional
    public void deleteUserById(String id) {
        logger.warn(">>>> Deleting user with id :" + id);
        Long deletedRecords = userRepository.deleteUserById(id);
        if (deletedRecords == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User was not found by id [%s]".formatted(id));
    }

    public User findUserByUsernameOrEmail(String username, String email) {
        return userRepository
                .findUserByUsernameOrEmail(username, Encryption.encrypt(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found"));
    }

    @Override
    public Boolean existUserByUsernameOrEmail(String username, String email) {
        return userRepository
                .existsUserByUsernameOrEmail(username, email);
    }

    private void validateUser(UserDTO userDTO) {
        if (userRepository.existsUserByUsername(userDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User already exist with username [%s]", userDTO.getUsername()));
        }
        if (userRepository.existsUserByEmail(Encryption.encrypt(userDTO.getEmail()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User already exist with email [%s]", userDTO.getEmail()));
        }
    }

    private User findByIdOrNotFound(String id) {
        return findByIdOrNotFound(id, Function.identity());
    }

    private <R> R findByIdOrNotFound(String id, Function<User, R> mapper) {
        return userRepository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id [%s]".formatted(id)));
    }

    private void validateUserBeforeUpdate(UserDTO userDTO, String id) {
        if (userRepository.existsUserByUsernameExcludeId(userDTO.getUsername(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User already exist with username [%s]", userDTO.getUsername()));
        }
        if (userRepository.existsUserByEmailExcludeId(Encryption.encrypt(userDTO.getEmail()), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User already exist with email [%s]", userDTO.getEmail()));
        }
    }

    @Scheduled(fixedRate = 86400000)
    public void execute() {
        userRepository.findAll()
                .forEach(user -> {
                    final var jwt = jwtRepository.getJwtTokensByCreatedAtIsGreaterThan(new Date(System.currentTimeMillis() - 259200000), user.getId());
                    user.setEnabled(!jwt.isEmpty());
                    if (jwt.isEmpty())
                        kafkaNotify.notifyUser("User is disabled");
                    userRepository.save(user);
                });
        System.out.println("Code for Enabled is being executed...");
    }
}
