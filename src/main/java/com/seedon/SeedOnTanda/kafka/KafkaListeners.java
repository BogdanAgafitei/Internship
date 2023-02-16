package com.seedon.SeedOnTanda.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "SeedOnTanda", groupId = "groupID")
    void listener(String data) {
        System.out.println("Received: " + data);
    }
}
