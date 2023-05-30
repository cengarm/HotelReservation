package com.example.hotelReservationSystem.wepApi.models;


import com.example.hotelReservationSystem.business.dtos.requests.campaignRequests.UseCampaignCodeRequest;
import com.example.hotelReservationSystem.business.dtos.requests.creditCardRequests.CreateCreditCardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePayment {

    @NotNull
    @Valid
    CreateCreditCardRequest createCreditCardRequest;

    UseCampaignCodeRequest useCampaignCodeRequest;

    @NotNull
    @Valid
    int roomBookingId;


}
