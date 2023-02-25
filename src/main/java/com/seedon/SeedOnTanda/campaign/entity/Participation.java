package com.seedon.SeedOnTanda.campaign.entity;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participation {

    private BigDecimal ticketPrice;
    @Max(105)
    private long maxTicketPerUser;
    private double chancesPerTicket;

}
