package com.seedon.SeedOnTanda.user.service;

import com.seedon.SeedOnTanda.common.request.PageableRequest;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO getUserById(String id) throws Exception;

    Page<UserDTO> getUsers(PageableRequest pageableRequest);

    UserDTO saveUser(UserDTO userDTO);

    UserDTO updateUser(String id, UserDTO userDTO);

    void deleteUserById(String id);

    User findUserByUsernameOrEmail(String username, String email);

    Boolean existUserByUsernameOrEmail(String username, String email);
}
