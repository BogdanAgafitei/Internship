package com.seedon.SeedOnTanda.campaign.controller;

import com.seedon.SeedOnTanda.campaign.entity.Campaign;
import com.seedon.SeedOnTanda.campaign.repository.CampaignRepository;
import com.seedon.SeedOnTanda.campaign.service.CampaignService;
import com.seedon.SeedOnTanda.common.request.PageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping()
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        return ResponseEntity.ok(campaignService.saveCampaign(campaign));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable String id, @RequestBody Campaign campaign){
        return ResponseEntity.ok(campaignService.updateCampaign(id,campaign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCampaign(@PathVariable String id){
        campaignService.deleteCampaign(id);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable String id){
        return ResponseEntity.ok(campaignService.findCampaignById(id));
    }
    @GetMapping
    public ResponseEntity<Page<Campaign>> getAllCampaigns(PageableRequest pageableRequest){
        return ResponseEntity.ok(campaignService.getAllCampaigns(pageableRequest));
    }
}
