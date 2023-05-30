package com.example.hotelReservationSystem.business.abstracts;

import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.entity.concretes.Campaign;
import com.example.hotelReservationSystem.business.dtos.responses.campaignResponses.CampaignListDto;
import com.example.hotelReservationSystem.business.dtos.requests.campaignRequests.CreateCampaignRequest;

import java.util.List;

public interface CampaignService {

    Result add(CreateCampaignRequest createCampaignRequest) throws BusinessException;

    DataResult<List<CampaignListDto>> getAll() throws BusinessException;

    DataResult<CampaignListDto> getById(int campaignId) throws BusinessException;

    DataResult<Campaign> getByCampaignCode(String campaignCode) throws BusinessException;


    void checkIfCapmaignStartDate(String campaignCode) throws BusinessException;

    void checkIfCapmaignFinishDate(String campaignCode) throws BusinessException;

    void checkIfCampaignCampaignQuantity(String campaignCode) throws BusinessException;

    void checkIfExistsByCampaignCode(String campaignCOde) throws BusinessException;


}
