package com.seedon.SeedOnTanda.user.service;

import com.seedon.SeedOnTanda.common.pagination.SeedOnPage;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.entity.User;
import org.springframework.data.domain.Page;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    UserDTO getUserById(String id) throws Exception;

    Page<UserDTO> getUsers(int page, int size, String[] sort_by, String direction);

    UserDTO saveUser(UserDTO userDTO) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    UserDTO updateUser(String id, UserDTO userDTO) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;


    void deleteUserById(String id);

    User findUserByUsernameOrEmail(String username, String email) throws Exception;

    Boolean existUserByUsernameOrEmail(String username, String email) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

}
