package com.example.hotelReservationSystem.business.abstracts;

import com.example.hotelReservationSystem.business.concretes.CreditCardManager;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.creditCardResponses.CreditCardListDto;
import com.example.hotelReservationSystem.business.dtos.requests.creditCardRequests.CreateCreditCardRequest;

import java.util.List;

public interface CreditCardService {
    // A service interface.
    Result add(CreateCreditCardRequest createCreditCardRequest) throws BusinessException;

    DataResult<List<CreditCardListDto>> getAll() throws BusinessException;

    DataResult<CreditCardListDto> getById(int creditCarId) throws BusinessException;

    DataResult<List<CreditCardListDto>> getAllCreditCardByCustomer_Id(int customerId) throws BusinessException;

    void checkSaveInformationAndSaveCreditCard(CreateCreditCardRequest createCreditCardRequest, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;

    void checkIfNotExistsByCustomer_Id(int customerId) throws BusinessException;


}
