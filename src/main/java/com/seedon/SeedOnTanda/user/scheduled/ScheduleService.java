package com.seedon.SeedOnTanda.user.scheduled;

import com.seedon.SeedOnTanda.jwt.repository.JwtRepository;
import com.seedon.SeedOnTanda.kafka.KafkaNotify;
import com.seedon.SeedOnTanda.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final JwtRepository jwtRepository;
    private final KafkaNotify kafkaNotify;

    public void execute() {
        userRepository.findAll()
                .forEach(user -> {
                    final var jwt = jwtRepository.getJwtTokensByCreatedAtIsGreaterThan(new Date(System.currentTimeMillis() - 259200000), user.getId());
                    user.setEnabled(!jwt.isEmpty());
                    if (jwt.isEmpty())
                        kafkaNotify.notifyUser("User is disabled");
                    userRepository.save(user);
                });
        System.out.println("Code for Enabled is being executed...");
    }
}
