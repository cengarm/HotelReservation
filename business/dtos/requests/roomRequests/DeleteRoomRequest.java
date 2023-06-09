package com.example.hotelReservationSystem.business.dtos.requests.roomRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRoomRequest {

    @NotNull
    private int id;
}
