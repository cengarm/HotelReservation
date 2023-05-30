package com.example.hotelReservationSystem.business.dtos.requests.roomRenovationRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRenovation {

    @NotNull
    private String roomRenovationDescription;
    @NotNull
    private int roomId;


}
