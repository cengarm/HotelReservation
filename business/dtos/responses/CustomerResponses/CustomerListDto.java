package com.example.hotelReservationSystem.business.dtos.responses.CustomerResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
