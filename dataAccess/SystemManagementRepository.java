package com.example.hotelReservationSystem.dataAccess;

import com.example.hotelReservationSystem.entity.concretes.SystemManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemManagementRepository extends JpaRepository<SystemManagement, Integer> {
    boolean existsByEmail(String email);


}
