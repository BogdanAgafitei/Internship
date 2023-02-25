package com.seedon.SeedOnTanda.campaign.repository;

import com.seedon.SeedOnTanda.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, String> {
    Long deleteCampaignById(String id);
    Optional<Campaign> findById(String id);

}
