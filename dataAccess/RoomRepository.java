package com.example.hotelReservationSystem.dataAccess;

import com.example.hotelReservationSystem.entity.concretes.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByHotelUserId(int id);

    boolean existsById(int roomId);

    List<Room> findByDailyPriceLessThanEqual(double dailyPrice);


}
