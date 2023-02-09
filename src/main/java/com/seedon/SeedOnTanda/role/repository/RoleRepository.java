package com.seedon.SeedOnTanda.role.repository;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findRoleByRoleName(RoleValues roles);

    Boolean existsRoleByRoleName(RoleValues roles);

    Long deleteRoleById(String id);

}
