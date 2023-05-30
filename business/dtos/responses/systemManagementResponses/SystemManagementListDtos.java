package com.example.hotelReservationSystem.business.dtos.responses.systemManagementResponses;

import com.example.hotelReservationSystem.core.utilities.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemManagementListDtos {
    private String email;
    private String password;
    private UserRole userRole;
    private String firstName;
    private String lastName;
}
