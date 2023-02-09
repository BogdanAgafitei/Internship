package com.seedon.SeedOnTanda.role.entity;

import com.seedon.SeedOnTanda.common.CommonBaseAbstractEntity;
import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.dto.RoleDTO;
import com.seedon.SeedOnTanda.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role extends CommonBaseAbstractEntity {
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleValues roleName;


    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role(RoleDTO roleDTO) {
        setId(roleDTO.id());
        roleName = roleDTO.roleName();
    }

    public Role(RoleValues roleValues) {
        roleName = roleValues;
    }

    public void setStates(RoleDTO roleDTO) {
        setRoleName(roleDTO.roleName());
    }

}
