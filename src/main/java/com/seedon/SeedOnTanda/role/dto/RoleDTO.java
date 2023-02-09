package com.seedon.SeedOnTanda.role.dto;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.entity.Role;
import jakarta.validation.constraints.NotBlank;

public record RoleDTO(
        String id,
        @NotBlank(message = "Role name should not be blank")
        RoleValues roleName) {


    public RoleDTO(Role role) {
        this(role.getId(), role.getRoleName());
    }
}
