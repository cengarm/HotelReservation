package com.example.hotelReservationSystem.business.adapters.posAdapters;

import com.example.hotelReservationSystem.business.outServices.AkbankPosService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class AkbankPosAdapter implements PosService {

    @Override
    public boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException {

        AkbankPosService akbankPosService = new AkbankPosService();

        if (!akbankPosService.makePayment(cardOwner, cardNumber, cardCvv, cardExpirationDate, totalPrice)) {
            throw new BusinessException("payment failed, Akbank");
        }
        return true;
    }
}
