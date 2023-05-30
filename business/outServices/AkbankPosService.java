package com.example.hotelReservationSystem.business.outServices;

public class AkbankPosService {

    public boolean makePayment(String cardOwner, String cardNumber, String cardCvv, String cardExpirationDate, double totalPrice) {

        return cardOwner.equals("Mustafa Cengar") || cardOwner.equals("");
    }

}