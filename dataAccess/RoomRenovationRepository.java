package com.example.hotelReservationSystem.dataAccess;

import com.example.hotelReservationSystem.entity.concretes.RoomRenovation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRenovationRepository extends JpaRepository<RoomRenovation, Integer> {
    boolean existsByRoomId(int id);

    RoomRenovation findByRoomId(int id);


}

