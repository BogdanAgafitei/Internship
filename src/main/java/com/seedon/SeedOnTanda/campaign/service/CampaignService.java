package com.seedon.SeedOnTanda.campaign.service;

import com.seedon.SeedOnTanda.campaign.entity.Campaign;
import com.seedon.SeedOnTanda.campaign.repository.CampaignRepository;
import com.seedon.SeedOnTanda.common.request.PageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public Campaign findCampaignById(String id){
        return campaignRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campaign was not found"));
    }

    public Campaign saveCampaign(Campaign campaign) {
        return campaignRepository.save(calculateDuration(campaign));
    }

    public Campaign updateCampaign(String id,Campaign campaign){
        final var existingCampaign = campaignRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found."));
        existingCampaign.updateCampaignState(campaign);
        return campaignRepository.save(existingCampaign);
    }

    public void deleteCampaign(String id){
        final var deleted = campaignRepository.deleteCampaignById(id);
        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campaign with [%s], not found".formatted(id));
        }
    }

    private Campaign calculateDuration(Campaign campaign){
        final var duration = Duration.between(campaign.getCampaignDetails().getStartDate(), campaign.getCampaignDetails().getEndDate());
        if(duration.toDays() <= 1){
            campaign.getCampaignDetails().setDurationHours(duration.toHours());
        }else {
            campaign.getCampaignDetails().setDurationDays(duration.toDays());
        }
        return campaign;
    }

    public Page<Campaign> getAllCampaigns(PageableRequest pageableRequest) {
        return campaignRepository.findAll(pageableRequest.createPageRequest());
    }
}
