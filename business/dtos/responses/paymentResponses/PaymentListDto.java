package com.example.hotelReservationSystem.business.dtos.responses.paymentResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentListDto {

    private int paymentId;
    private double totalPrice;
    private int roomBookingId;


}
