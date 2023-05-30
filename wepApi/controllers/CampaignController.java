package com.example.hotelReservationSystem.wepApi.controllers;

import com.example.hotelReservationSystem.business.abstracts.CampaignService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.campaignResponses.CampaignListDto;
import com.example.hotelReservationSystem.business.dtos.requests.campaignRequests.CreateCampaignRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

    // This is a constructor injection.
    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    // This is a controller class.
    @PostMapping("/add")
    public Result add(@RequestBody CreateCampaignRequest createCampaignRequest) throws BusinessException {
        return this.campaignService.add(createCampaignRequest);
    }

    @GetMapping("/getAll")
    public DataResult<List<CampaignListDto>> getAll() throws BusinessException {
        return this.campaignService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<CampaignListDto> getById(int campaignId) throws BusinessException {
        return this.campaignService.getById(campaignId);
    }
}
