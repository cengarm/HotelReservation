package com.example.hotelReservationSystem.business.dtos.responses.campaignResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignListDto {

    private String campaignName;
    private int percentDiscount;
    private double campaignQuantity;
    private double campaignQuantityUsed;
    private LocalDate campaignStartDate;
    private LocalDate campaignFinishDate;
}
