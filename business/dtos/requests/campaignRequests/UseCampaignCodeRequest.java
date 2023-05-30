package com.example.hotelReservationSystem.business.dtos.requests.campaignRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UseCampaignCodeRequest {

    private String campaignCode;
}
