package com.example.hotelReservationSystem.business.dtos.requests.hotelRequests;

import com.example.hotelReservationSystem.core.utilities.enums.Cities;
import com.example.hotelReservationSystem.core.utilities.enums.HotelStar;
import com.example.hotelReservationSystem.core.utilities.enums.HotelType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHotelRequest {

    @NotNull
    private int id;

    private String hotelName;

    private long hotelTel;


    private HotelStar hotelStar;


    private HotelType hotelType;


    private Cities cities;
}
