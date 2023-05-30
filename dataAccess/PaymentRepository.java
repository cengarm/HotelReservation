package com.example.hotelReservationSystem.dataAccess;

import com.example.hotelReservationSystem.entity.concretes.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment getAllByRoomBooking_Id(int roomBokingId);

    boolean existsByRoomBooking_Id(int roomBookingId);

}
