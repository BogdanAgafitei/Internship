package com.seedon.SeedOnTanda.role.service;


import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.dto.RoleDTO;
import com.seedon.SeedOnTanda.role.entity.Role;

import java.util.List;

public interface RoleService {

    RoleDTO getRoleById(String id);

    List<RoleDTO> getRoles();

    RoleDTO saveRole(RoleDTO roleDTO);

    RoleDTO updateRole(String id, RoleDTO roleDTO);

    void deleteRoleById(String id);

    List<Role> getRolesByName(List<RoleValues> roleValues);

    Role getRoleByName(RoleValues value);

    Boolean existsRoleByRoleName(RoleValues roleValues);
}
