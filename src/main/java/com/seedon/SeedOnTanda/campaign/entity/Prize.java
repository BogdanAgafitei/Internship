package com.seedon.SeedOnTanda.campaign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prize implements Serializable {

    private Long numberPoolOfWiners;
    private float prizePercentageForEachWinner;

}