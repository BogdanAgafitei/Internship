package com.seedon.SeedOnTanda.auth.utils;

import com.seedon.SeedOnTanda.auth.userdetails.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationUtils {

    public static String extractUserIdFromAuthContext() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .map(UserDetailsImpl.class::cast)
                .map(UserDetailsImpl::getId)
                .orElse(null);
    }

}
