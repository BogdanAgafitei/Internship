package com.seedon.SeedOnTanda.campaign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seedon.SeedOnTanda.common.CommonBaseAbstractEntity;
import com.seedon.SeedOnTanda.storage.entity.ImageData;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Currency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDetails implements Serializable {

   @NotBlank(message = "Invalid field: Please enter your first name")
   private String name;

   private String projectHost;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
   private LocalDateTime startDate;


   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
   private LocalDateTime endDate;

   private long durationDays;
   private long durationHours;

   private ImageData coverImage;

   private ImageData logo;

   private String[] tags;

   @DecimalMax("1050")
   @Currency("USDT")
   private BigDecimal maxAmountToRaise;


}
