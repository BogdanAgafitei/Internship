package com.seedon.SeedOnTanda.jwt.service;

import com.seedon.SeedOnTanda.enums.statuses.Statuses;
import com.seedon.SeedOnTanda.jwt.entity.Jwt;
import com.seedon.SeedOnTanda.jwt.repository.JwtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Scheduled(fixedRate = 6000000)
    public void execute() {
        jwtRepository.setExpiredStatus(new Date(System.currentTimeMillis()));
        System.out.println("Code for setting jwts expired is being executed...");
    }
}
