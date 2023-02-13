package com.seedon.SeedOnTanda.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seedon.SeedOnTanda.common.CommonBaseAbstractEntity;
import com.seedon.SeedOnTanda.role.entity.Role;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.state.StateType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User extends CommonBaseAbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(columnDefinition = "json", name = "state")
    @Type(JsonType.class)
    @JsonProperty("state")
    private StateType state;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();


    public User(UserDTO userDTO) {
        setId(userDTO.getId());
        firstName = userDTO.getFirstName();
        lastName = userDTO.getLastName();
        username = userDTO.getUsername();
        email = userDTO.getEmail();
        password = userDTO.getPassword();
        phoneNumber = userDTO.getPhoneNumber();
    }

    public User(String id, String firstName, String lastName, String username, String email, String password, String phoneNumber) {
        setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void updateStates(User updated, List<Role> roles, String password) {
        setFirstName(updated.getFirstName());
        setLastName(updated.getLastName());
        setUsername(updated.getUsername());
        setEmail(updated.getEmail());
        setPassword(password);
        setPhoneNumber(updated.getPhoneNumber());
        setRoles(roles);
    }
}
