package com.example.hotelReservationSystem.business.dtos.requests.roomRequests;

import com.example.hotelReservationSystem.core.utilities.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {

    @NotNull
    private String description;

    @NotNull
    private int personCapacity;

    @NotNull
    private int floor;

    @NotNull
    private double dailyPrice;

    @NotNull
    private int hotelUserId;

    @NotNull
    private RoomType roomType;


}
