package com.seedon.SeedOnTanda.auth.userdetails;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDetailsImpl implements UserDetails {

    private String id;
    @Getter
    private String email;
    private List<RoleValues> roles;

    public UserDetailsImpl(Claims claims, List<RoleValues> roles) {
        id = claims.getId();
        email = claims.getSubject();
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> list = new HashSet<>();
        roles.forEach(roles1 -> list.add(new SimpleGrantedAuthority(roles1.name())));
        return list;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
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
