package com.seedon.SeedOnTanda.common;

import com.seedon.SeedOnTanda.role.entity.Role;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Component
@Data
public class Encryption {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public static void prepareSecreteKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            prepareSecreteKey("secret");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            prepareSecreteKey("secret");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
        }
        return null;
    }

    public static UserDTO userToDtoMapper(User user) {
        return new UserDTO(
                user.getId(),
                decrypt(user.getFirstName()),
                decrypt(user.getLastName()),
                user.getUsername(),
                decrypt(user.getEmail()),
                user.getPassword(),
                decrypt(user.getPhoneNumber()),
                user.getRoles().stream()
                        .map(Role::getRoleName)
                        .toList());
    }

    public static User userDtoToUserMapper(UserDTO userDTO) {
        return new User(
                userDTO.id(),
                encrypt(userDTO.firstName()),
                encrypt(userDTO.lastName()),
                userDTO.username(),
                encrypt(userDTO.email()),
                userDTO.password(),
                encrypt(userDTO.phoneNumber())
        );
    }
}







