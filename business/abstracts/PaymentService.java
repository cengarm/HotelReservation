package com.example.hotelReservationSystem.business.abstracts;

import com.example.hotelReservationSystem.wepApi.models.MakePayment;
import com.example.hotelReservationSystem.business.concretes.CreditCardManager;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.paymentResponses.PaymentListDto;

import java.util.List;

public interface PaymentService {
    // A service interface.

    Result makePayment(MakePayment makePayment, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException;

    DataResult<List<PaymentListDto>> getAll() throws BusinessException;

    DataResult<PaymentListDto> getById(int paymentId) throws BusinessException;

    void checkIfExistByPaymentId(int paymentId) throws BusinessException;


}
