package com.seedon.SeedOnTanda.enums.roles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public enum RoleValues {
    ROLE_ADMIN, ROLE_USER;

    @JsonCreator
    public static RoleValues mapRole(@JsonProperty @NotNull String role) {
        return RoleValues.valueOf("ROLE_" + role);
    }

}
