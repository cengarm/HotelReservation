package com.example.hotelReservationSystem.business.abstracts;

import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.invoiceResponses.InvoiceListDto;
import com.example.hotelReservationSystem.business.dtos.requests.invoiceRequest.CreateInvoiceRequest;

import java.util.List;

public interface InvoiceService {

    // A service interface.

    Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

    DataResult<List<InvoiceListDto>> getAll();

    DataResult<InvoiceListDto> getById(int invoiceId) throws BusinessException;

    DataResult<InvoiceListDto> getInvociceByPaymetn_Id(int paymentId) throws BusinessException;

    DataResult<List<InvoiceListDto>> getAllByRoomBooking_Id(int roomBookingId) throws BusinessException;

    DataResult<List<InvoiceListDto>> getAllByCustomer_Id(int customerId) throws BusinessException;


    void checkIfNotExistsByCustomer_Id(int customerId) throws BusinessException;

    void checkIfnotExistsByRoomBooking_Id(int roomBookingId) throws BusinessException;

    void createAddInvoice(int roomBookingId, int paymentId) throws BusinessException;

    void checkIfNotExistsByPayment_Id(int paymentId) throws BusinessException;
}
