package com.example.hotelReservationSystem.dataAccess;

import com.example.hotelReservationSystem.entity.abstracts.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
