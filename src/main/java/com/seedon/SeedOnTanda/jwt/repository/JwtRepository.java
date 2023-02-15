package com.seedon.SeedOnTanda.jwt.repository;

import com.seedon.SeedOnTanda.enums.statuses.Statuses;
import com.seedon.SeedOnTanda.jwt.entity.Jwt;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, String> {
    Optional<Jwt> getJwtByUserIdAndStatus(String userId, Statuses status);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update jwts set status = 'EXPIRED' WHERE expiration_time < :currentTime")
    void setExpiredStatus(@Param("currentTime") Date currentTime);

    @Transactional
    @Query(nativeQuery = true, value = "select * from jwts WHERE creation_date > :daysOfInactivity AND user_id = :user_id")
    List<Jwt> getJwtTokensByCreatedAtIsGreaterThan(@Param("daysOfInactivity") Date daysOfInactivity, @Param("user_id") String user_id);
}
