package com.seedon.SeedOnTanda.auth;

import com.seedon.SeedOnTanda.auth.config.JwtService;
import com.seedon.SeedOnTanda.role.entity.Role;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.entity.User;
import com.seedon.SeedOnTanda.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    String getUsernameOrEmail(AuthenticationRequest request) {
        return Optional.ofNullable(request.getEmail())
                .orElseGet(request::getUsername);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        getUsernameOrEmail(request),
                        request.getPassword()
                )
        );
        var user = userService.findUserByUsernameOrEmail(getUsernameOrEmail(request), getUsernameOrEmail(request));
        var jwtToken = jwtService.generateToken(new SeedOnUserDetails(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse register(RegisterRequest request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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
        final var jwtToken = jwtService.generateToken(new SeedOnUserDetails(new User(user)));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
