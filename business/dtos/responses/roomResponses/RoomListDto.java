package com.example.hotelReservationSystem.business.dtos.responses.roomResponses;

import com.example.hotelReservationSystem.core.utilities.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListDto {

    private int id;
    private String description;
    private int personCapacity;
    private int floor;
    private RoomType roomType;


}
