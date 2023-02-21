package com.seedon.SeedOnTanda.user.controller;

import com.seedon.SeedOnTanda.common.request.PageableRequest;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users")
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findUsers(PageableRequest pageableRequest) {
        return ResponseEntity.ok(userService.getUsers(pageableRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(201).build();
    }

}
