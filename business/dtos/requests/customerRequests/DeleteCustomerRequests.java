package com.example.hotelReservationSystem.business.dtos.requests.customerRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCustomerRequests {

    @NotNull
    private int id;
}
