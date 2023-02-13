package com.seedon.SeedOnTanda.auth;

import com.seedon.SeedOnTanda.auth.config.JwtTokenService;
import com.seedon.SeedOnTanda.enums.statuses.Statuses;
import com.seedon.SeedOnTanda.jwt.entity.Jwt;
import com.seedon.SeedOnTanda.jwt.service.JwtService;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.entity.User;
import com.seedon.SeedOnTanda.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final JwtService jwtService;

    String getUsernameOrEmail(AuthenticationRequest request) {
        return Optional.ofNullable(request.getEmail())
                .orElseGet(request::getUsername);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        getUsernameOrEmail(request),
                        request.getPassword()
                )
        );
        var user = userService.findUserByUsernameOrEmail(getUsernameOrEmail(request), getUsernameOrEmail(request));
        var jwtToken = jwtTokenService.generateToken(new SeedOnUserDetails(user));
        saveJwt(jwtToken, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse register(RegisterRequest request) {
        final var user = new UserDTO(
                null,
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getPhoneNumber(),
                request.getRole());
        userService.saveUser(user);
        final var jwtToken = jwtTokenService.generateToken(new SeedOnUserDetails(new User(user)));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveJwt(String jwtToken, User user) {
        final var jwt = new Jwt();
        final var oldToken = jwtService.getJwtByUserIdAndStatus(user.getId(), Statuses.VALID);
        oldToken.ifPresent(value -> value.setStatus(Statuses.EXPIRED));
        final var jwtSetted = setJwtProperties(jwt, jwtToken, user);
        jwtService.saveJwt(jwtSetted);

    }

    private Jwt setJwtProperties(Jwt jwt, String jwtToken, User user) {
        jwt.setStatus(Statuses.VALID);
        jwt.setExpirationTime(jwtTokenService.extractExpiration(jwtToken));
        jwt.setUserId(user.getId());
        jwt.setToken(jwtToken);
        return jwt;
    }
}
