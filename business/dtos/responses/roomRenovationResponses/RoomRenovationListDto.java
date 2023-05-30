package com.example.hotelReservationSystem.business.dtos.responses.roomRenovationResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRenovationListDto {

    private int id;
    private int roomID;
    private String roomRenovationDescription;

}
