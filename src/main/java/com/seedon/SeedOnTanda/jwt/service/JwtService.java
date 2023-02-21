package com.seedon.SeedOnTanda.jwt.service;

import com.seedon.SeedOnTanda.enums.statuses.Statuses;
import com.seedon.SeedOnTanda.jwt.entity.Jwt;
import com.seedon.SeedOnTanda.jwt.repository.JwtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepository;

    public void saveJwt(Jwt jwt) {
        jwtRepository.save(jwt);
    }

    public Optional<Jwt> getJwtByUserIdAndStatus(String userId, Statuses status) {
        return jwtRepository.getJwtByUserIdAndStatus(userId, status);
    }
}
