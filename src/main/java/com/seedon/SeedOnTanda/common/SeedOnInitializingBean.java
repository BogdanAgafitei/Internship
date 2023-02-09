package com.seedon.SeedOnTanda.common;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.dto.RoleDTO;
import com.seedon.SeedOnTanda.role.service.RoleService;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class SeedOnInitializingBean implements InitializingBean {
    private final UserService userService;
    private final RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(SeedOnInitializingBean.class);

    @Override
    public void afterPropertiesSet() {
        logger.info("In initializing bean");
        UserDTO userDTO = new UserDTO(null,
                "admin",
                "admin",
                "admin",
                "admin@seedon.com",
                "admin",
                "admin",
                List.of(RoleValues.ROLE_ADMIN));
        Stream.of(RoleValues.values())
                .filter(roleValues -> !roleService.existsRoleByRoleName(roleValues))
                .forEach(roleValues -> roleService.saveRole(new RoleDTO(null, roleValues)));
        Optional.of(userDTO)
                .filter(userDTO1 -> {

                    try {
                        return !userService.existUserByUsernameOrEmail(userDTO1.username(), userDTO1.email());
                    } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                             IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
                        throw new RuntimeException(e);
                    }

                })
                .map(userDTO1 -> {
                    try {
                        return userService.saveUser(userDTO);
                    } catch (InvalidAlgorithmParameterException | NoSuchPaddingException |
                             IllegalBlockSizeException | UnsupportedEncodingException |
                             NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
