package com.seedon.SeedOnTanda.jwt.entity;

import com.seedon.SeedOnTanda.common.CommonBaseAbstractEntity;
import com.seedon.SeedOnTanda.enums.statuses.Statuses;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Data
@Entity
@Table(name = "jwts")
@DynamicUpdate
@DynamicInsert
public class Jwt extends CommonBaseAbstractEntity {
    @Column(name = "expiration_time")
    private Date expirationTime;
    @Column(name = "token")
    private String token;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Statuses status;
    @Column(name = "user_id")
    private String userId;
}
