package com.seedon.SeedOnTanda.role.controller;

import com.seedon.SeedOnTanda.role.dto.RoleDTO;
import com.seedon.SeedOnTanda.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/roles")
public class RoleRestController {

    private final RoleService roleService;


    @GetMapping
    public ResponseEntity<List<RoleDTO>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> saveRole(@Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.saveRole(roleDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable String id, @Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.updateRole(id, roleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable String id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.ok("Role was deleted with id [%s]".formatted(id));
    }
}
