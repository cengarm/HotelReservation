package com.example.hotelReservationSystem.dataAccess;

import com.example.hotelReservationSystem.entity.concretes.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {


    Campaign getCampaignByCampaignCode(String campaignCode);

    Campaign getByCampaignCode(String campaingCode);

    boolean existsByCampaignCode(String campaignCode);
}
