package com.example.hotelReservationSystem.business.concretes;

import com.example.hotelReservationSystem.dataAccess.PaymentRepository;
import com.example.hotelReservationSystem.wepApi.models.MakePayment;
import com.example.hotelReservationSystem.business.abstracts.*;
import com.example.hotelReservationSystem.business.adapters.posAdapters.PosService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.mapping.ModelMapperService;
import com.example.hotelReservationSystem.core.utilities.messages.BusinessMessages;
import com.example.hotelReservationSystem.core.utilities.result.*;
import com.example.hotelReservationSystem.entity.concretes.Campaign;
import com.example.hotelReservationSystem.entity.concretes.Payment;
import com.example.hotelReservationSystem.business.dtos.responses.paymentResponses.PaymentListDto;
import com.example.hotelReservationSystem.business.dtos.responses.roomBookingResponses.RoomBookingListDto;
import com.example.hotelReservationSystem.business.dtos.requests.hotelRequests.paymentRequests.CreatePaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentManager implements PaymentService {

    // A constructor injection.
    private final PaymentRepository paymentRepository;
    private final RoomBookingService roomBookingService;
    private final ModelMapperService modelMapperService;
    private final PosService posService;
    private final InvoiceService invoiceService;
    private final CreditCardService creditCardService;
    private final CampaignService campaignService;

    @Autowired
    public PaymentManager( PaymentRepository paymentRepository, RoomBookingService roomBookingService, ModelMapperService modelMapperService, PosService posService, InvoiceService invoiceService, CreditCardService creditCardService, CampaignService campaignService) {
        this.paymentRepository = paymentRepository;
        this.roomBookingService = roomBookingService;
        this.modelMapperService = modelMapperService;
        this.posService = posService;
        this.invoiceService = invoiceService;
        this.creditCardService = creditCardService;
        this.campaignService = campaignService;
    }


    @Override
    public DataResult<List<PaymentListDto>> getAll() throws BusinessException {
        List<Payment> payments = this.paymentRepository.findAll();
        List<PaymentListDto> paymentListDtos = payments.stream().map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(paymentListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public DataResult<PaymentListDto> getById(int paymentId) throws BusinessException {
        checkIfExistByPaymentId(paymentId);
        Payment payment = this.paymentRepository.getById(paymentId);
        PaymentListDto paymentListDto = this.modelMapperService.forDto().map(payment, PaymentListDto.class);
        return new SuccessDataResult<>(paymentListDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public Result makePayment(MakePayment makePayment, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        try {
            checkAllCommonCampaignValidation(makePayment);
            checkIfExistsByRoomBookingId(makePayment.getRoomBookingId());
            calculatingTheAmountPayable(makePayment);
            this.posService.payment(makePayment.getCreateCreditCardRequest().getCardNumber(), makePayment.getCreateCreditCardRequest().getCardOwner(), makePayment.getCreateCreditCardRequest().getCardCvv(), makePayment.getCreateCreditCardRequest().getCardExpirationDate(), makePayment.getCreateCreditCardRequest().getCustomerId());
            checkCampaignCodeisNull(makePayment);
            runPaymentSuccessorForRoomBooking(makePayment, cardSaveInformation);
            log.info(BusinessMessages.LogMessages.PAYMENT_SUCCES + " Paymenetmanager -> MakePayment Operation");
            return new SuccessResult(BusinessMessages.PaymentMessages.PAYMENT_AND_BOOKING_ROOMSUCCESSFULLY);
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.PAYMENT_FAILED + " PaymantManager -> MakePayment Operation");
            return new ErrorResult(e.getMessage());
        }
    }


    @Override
    public void checkIfExistByPaymentId(int paymentId) throws BusinessException {
        log.info(BusinessMessages.LogMessages.CHECKING_DB + " PaymentManager -> CheckIfExistByPaymentId Operation");
        if (!this.paymentRepository.existsById(paymentId)) {
            throw new BusinessException(BusinessMessages.PaymentMessages.PAYMENT_ID_NOT_FOUND + paymentId);
        }
    }

    @Transactional(rollbackFor = BusinessException.class)
    // A function that is used to make a payment for a room booking.
    void runPaymentSuccessorForRoomBooking(MakePayment makePayment, CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        RoomBookingListDto roomBooking = this.roomBookingService.getById(makePayment.getRoomBookingId()).getData();
        int roomBookingId = makePayment.getRoomBookingId();
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
        createPaymentRequest.setRoomBookingId(roomBookingId);
        createPaymentRequest.setTotalPrice(roomBooking.getTotalPrice());
        makePayment.getCreateCreditCardRequest().setCustomerId(roomBooking.getCustomerId());
        Payment payment = this.modelMapperService.forDto().map(createPaymentRequest, Payment.class);
        int paymentId = this.paymentRepository.save(payment).getId();
        this.creditCardService.checkSaveInformationAndSaveCreditCard(makePayment.getCreateCreditCardRequest(), cardSaveInformation);
        this.invoiceService.createAddInvoice(roomBookingId, paymentId);
    }


    private void checkIfExistsByRoomBookingId(int roomBookingId) throws BusinessException {
        if (this.paymentRepository.existsByRoomBooking_Id(roomBookingId)) {
            throw new BusinessException(BusinessMessages.PaymentMessages.PAYMEYNT_ALREADY_DONE + roomBookingId);
        }
    }


    private void checkAllCommonCampaignValidation(MakePayment makePayment) throws BusinessException {
        this.campaignService.checkIfExistsByCampaignCode(makePayment.getUseCampaignCodeRequest().getCampaignCode());
        this.campaignService.checkIfCapmaignStartDate(makePayment.getUseCampaignCodeRequest().getCampaignCode());
        this.campaignService.checkIfCapmaignFinishDate(makePayment.getUseCampaignCodeRequest().getCampaignCode());
        this.campaignService.checkIfCampaignCampaignQuantity(makePayment.getUseCampaignCodeRequest().getCampaignCode());
    }


    private void checkCampaignCodeisNull(MakePayment makePayment) throws BusinessException {
        this.campaignService.checkIfExistsByCampaignCode(makePayment.getUseCampaignCodeRequest().getCampaignCode());
        Campaign campaign = this.campaignService.getByCampaignCode(makePayment.getUseCampaignCodeRequest().getCampaignCode()).getData();
        if (campaign != null) {
            campaign.setCampaignQuantityUsed(campaign.getCampaignQuantityUsed() + 1);
        }
    }

    private void calculatingTheAmountPayable(MakePayment makePayment) throws BusinessException {
        RoomBookingListDto roomBooking = this.roomBookingService.getById(makePayment.getRoomBookingId()).getData();
        Campaign campaign = this.campaignService.getByCampaignCode(makePayment.getUseCampaignCodeRequest().getCampaignCode()).getData();
        if (campaign != null) {
            double totalPrice = roomBooking.getTotalPrice() - (roomBooking.getTotalPrice() * campaign.getPercentDiscount() / 100);
        } else {
            double totalPrice = roomBooking.getTotalPrice();
        }

    }


}
