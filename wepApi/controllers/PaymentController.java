package com.example.hotelReservationSystem.wepApi.controllers;

import com.example.hotelReservationSystem.wepApi.models.MakePayment;
import com.example.hotelReservationSystem.business.abstracts.PaymentService;
import com.example.hotelReservationSystem.business.concretes.CreditCardManager;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.paymentResponses.PaymentListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    // A constructor injection.
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // This is a controller class.
    @GetMapping("/getAll")
    public DataResult<List<PaymentListDto>> getAll() throws BusinessException {
        return this.paymentService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<PaymentListDto> getById(int paymentId) throws BusinessException {
        return this.paymentService.getById(paymentId);
    }

    @PostMapping("/makePaymnet")
    public Result makePayment(@RequestBody MakePayment makePayment, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        return this.paymentService.makePayment(makePayment, cardSaveInformation);
    }
}
