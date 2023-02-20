package com.seedon.SeedOnTanda.user.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Scheduled(fixedRate = 86400000)
    public void execute() {
        scheduleService.execute();
    }
}
