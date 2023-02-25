package com.seedon.SeedOnTanda.campaign.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seedon.SeedOnTanda.common.CommonBaseAbstractEntity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "campaigns")
@EqualsAndHashCode(callSuper = true)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Campaign extends CommonBaseAbstractEntity {


    @Column(columnDefinition = "jsonb", name = "campaign_details")
    @Type(JsonType.class)
    private CampaignDetails campaignDetails;

    @Column(columnDefinition = "jsonb", name = "prize")
    @Type(JsonType.class)
    private Prize prize;

    @Column(columnDefinition = "jsonb", name = "commission")
    @Type(JsonType.class)
    private Commission commission;

    @Column(columnDefinition = "jsonb", name = "participation")
    @Type(JsonType.class)
    private Participation participation;

    public void updateCampaignState(Campaign campaign) {
        setCampaignDetails(campaign.getCampaignDetails());
        setPrize(campaign.getPrize());
        setCommission(campaign.getCommission());
        setParticipation(campaign.getParticipation());
    }
}
