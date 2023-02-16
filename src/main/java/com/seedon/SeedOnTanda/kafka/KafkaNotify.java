package com.seedon.SeedOnTanda.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaNotify {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public void notifyUser(String message) {
        kafkaTemplate.send("SeedOnTanda", message);
    }
}
