package com.example.hotelReservationSystem.business.concretes;

import com.example.hotelReservationSystem.business.abstracts.CampaignService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.mapping.ModelMapperService;
import com.example.hotelReservationSystem.core.utilities.messages.BusinessMessages;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.core.utilities.result.SuccessDataResult;
import com.example.hotelReservationSystem.core.utilities.result.SuccessResult;
import com.example.hotelReservationSystem.dataAccess.CampaignRepository;
import com.example.hotelReservationSystem.entity.concretes.Campaign;
import com.example.hotelReservationSystem.business.dtos.responses.campaignResponses.CampaignListDto;
import com.example.hotelReservationSystem.business.dtos.requests.campaignRequests.CreateCampaignRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignManager implements CampaignService {


    private final CampaignRepository campaignDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CampaignManager(CampaignRepository campaignDao, ModelMapperService modelMapperService) {
        this.campaignDao = campaignDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public Result add(CreateCampaignRequest createCampaignRequest) throws BusinessException {
        Campaign campaign = this.modelMapperService.forRequest().map(createCampaignRequest, Campaign.class);
        this.campaignDao.save(campaign);
        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }


    @Override
    public DataResult<List<CampaignListDto>> getAll() throws BusinessException {
        List<Campaign> campaigns = this.campaignDao.findAll();
        List<CampaignListDto> campaignListDtos = campaigns.stream().map(campaign -> this.modelMapperService.forDto().map(campaign, CampaignListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(campaignListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CampaignListDto> getById(int campaignId) throws BusinessException {
        Campaign campaign = this.campaignDao.getById(campaignId);
        CampaignListDto campaignListDto = this.modelMapperService.forDto().map(campaign, CampaignListDto.class);
        return new SuccessDataResult<>(campaignListDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public DataResult<Campaign> getByCampaignCode(String campaignCode) throws BusinessException {
        Campaign campaign = this.campaignDao.getByCampaignCode(campaignCode);
        return new SuccessDataResult<>(campaign, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public void checkIfCapmaignStartDate(String campaignCode) throws BusinessException {
        Campaign campaign = this.campaignDao.getCampaignByCampaignCode(campaignCode);
        if (campaign != null) {
            if (campaign.getCampaignStartDate().isAfter(LocalDate.now())) {
                throw new BusinessException(BusinessMessages.CampaignMessages.CAMPAIG_NOT_STARTED + campaignCode);
            }
        }
    }


    @Override
    public void checkIfCapmaignFinishDate(String campaignCode) throws BusinessException {
        Campaign campaign = this.campaignDao.getCampaignByCampaignCode(campaignCode);
        if (campaign != null) {
            if (campaign.getCampaignFinishDate().isBefore(LocalDate.now())) {
                throw new BusinessException(BusinessMessages.CampaignMessages.CAMPAIGN_EXPIRED + campaignCode);
            }
        }
    }


    @Override
    public void checkIfCampaignCampaignQuantity(String campaignCode) throws BusinessException {
        Campaign campaign = this.campaignDao.getCampaignByCampaignCode(campaignCode);
        if (campaign != null) {
            if (campaign.getCampaignQuantity() == campaign.getCampaignQuantityUsed()) {
                throw new BusinessException(BusinessMessages.CampaignMessages.CAPIGN_CODE_NOT_AMOUNT + campaignCode);
            }
        }
    }


    @Override
    public void checkIfExistsByCampaignCode(String campaignCode) throws BusinessException {
        if (campaignCode.length() == 7) {
            if (!this.campaignDao.existsByCampaignCode(campaignCode)) {
                throw new BusinessException(BusinessMessages.CampaignMessages.CAPIGN_CODE_NOT_FOUNT + campaignCode);
            }
        }
    }
}


