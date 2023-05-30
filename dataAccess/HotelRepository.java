package com.example.hotelReservationSystem.dataAccess;

import com.example.hotelReservationSystem.entity.concretes.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    Hotel getById(int hotelId);

    boolean existsByEmail(String email);


    Hotel findByEmail(String email);


}

