package com.seedon.SeedOnTanda.user.dto;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.entity.Role;
import com.seedon.SeedOnTanda.user.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UserDTO(
        String id,
        @NotBlank(message = "Your first name may not to be blank")
        String firstName,
        @NotBlank(message = "Your last name may not be blank")
        String lastName,
        @NotBlank(message = "Your username may not be blank")
        @Column(unique = true)
        String username,
        @Column(unique = true)
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Your email is not a valid email")
        @NotBlank(message = "Your email may not be blank")
        String email,
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
                message = "Your password is not valid( Password must contain:     1 digit " +
                        " 1 lowercase letter " +
                        " 1 uppercase letter " +
                        " 1 special character " +
                        " a length between 8 and 20)")
        @NotBlank(message = "Your password may not be blank")
        String password,
        @NotBlank(message = "Your phone number may not be blank")
        String phoneNumber,

        List<RoleValues> roles) {


    public UserDTO(User user) {
        this(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getRoles().stream()
                        .map(Role::getRoleName)
                        .toList());
    }

    public UserDTO(User user, List<RoleValues> roleList) {
        this(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                roleList);
    }


}
