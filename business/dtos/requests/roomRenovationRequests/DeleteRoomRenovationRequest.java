package com.example.hotelReservationSystem.business.dtos.requests.roomRenovationRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRoomRenovationRequest {
    @NotNull
    private int roomId;


}
