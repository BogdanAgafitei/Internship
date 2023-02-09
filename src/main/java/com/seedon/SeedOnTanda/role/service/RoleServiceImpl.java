package com.seedon.SeedOnTanda.role.service;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.dto.RoleDTO;
import com.seedon.SeedOnTanda.role.entity.Role;
import com.seedon.SeedOnTanda.role.repository.RoleRepository;
import com.seedon.SeedOnTanda.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public RoleDTO getRoleById(String id) {
        logger.info(">>> Getting role from database");
        return new RoleDTO(findByIdOrNotFound(id));
    }


    @Override
    public List<RoleDTO> getRoles() {
        logger.info(">>> Getting list of roles from database");
        return roleRepository.findAll()
                .stream()
                .map(RoleDTO::new)
                .toList();
    }

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {
        validateRole(roleDTO);
        final var user = new Role();
        user.setRoleName(RoleValues.valueOf(roleDTO.roleName().name()));
        logger.info(">>>> Saving a role to database >>>>");
        return new RoleDTO(roleRepository.save(user));
    }

    @Override
    public RoleDTO updateRole(String id, RoleDTO roleDTO) {
        final var existingRole = findByIdOrNotFound(id);
        logger.info(">>>> Updating role with id : " + existingRole.getId());
        existingRole.setStates(roleDTO);
        return new RoleDTO(roleRepository.save(existingRole));
    }

    @Override
    public void deleteRoleById(String id) {
        logger.warn(">>>> Deleting role with id :" + id);
        var deletedRecords = roleRepository.deleteRoleById(id);
        if (deletedRecords == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role was not found with id [%s]".formatted(id));
        }
    }

    @Override
    public List<Role> getRolesByName(List<RoleValues> roleValues) {
        var roles = new ArrayList<Role>();
        roleValues
                .forEach(role -> roles
                        .add(roleRepository
                                .findRoleByRoleName(role)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role was not found"))));
        return roles;
    }


    @Override
    public Role getRoleByName(RoleValues value) {
        return roleRepository
                .findRoleByRoleName(value)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role was not found"));
    }

    @Override
    public Boolean existsRoleByRoleName(RoleValues roleValues) {
        return roleRepository.existsRoleByRoleName(roleValues);
    }

    private Role findByIdOrNotFound(String id) {
        return findByIdOrNotFound(id, Function.identity());
    }

    private <R> R findByIdOrNotFound(String id, Function<Role, R> mapper) {
        return roleRepository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found for id [%s]".formatted(id)));
    }

    private void validateRole(RoleDTO roleDTO) {
        if (roleRepository.existsRoleByRoleName(roleDTO.roleName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Role already exist with username [%s]", roleDTO.roleName()));
        }
    }

}
