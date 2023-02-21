package com.seedon.SeedOnTanda.auth.userdetails;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.entity.Role;
import com.seedon.SeedOnTanda.user.entity.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class SeedOnUserDetails implements UserDetails {
    private String id;

    private String firstName;

    private String lastName;

    @Getter
    private String email;

    private String username;

    private String password;

    private String phoneNumber;

    private List<RoleValues> roles;

    public SeedOnUserDetails(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        username = user.getUsername();
        password = user.getPassword();
        phoneNumber = user.getPhoneNumber();
        roles = user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        roles
                .forEach(roleValues -> list.add(new SimpleGrantedAuthority(roleValues.name())));
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
