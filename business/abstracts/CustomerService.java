package com.example.hotelReservationSystem.business.abstracts;

import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.entity.concretes.Customer;
import com.example.hotelReservationSystem.business.dtos.responses.CustomerResponses.CustomerListDto;
import com.example.hotelReservationSystem.business.dtos.requests.customerRequests.CreateCustomerRequest;

import java.util.List;

public interface CustomerService {
    // A service interface.
    Result add(CreateCustomerRequest createCustomerRequests) throws BusinessException;

    Result deleteByCustomerId(int id) throws BusinessException;

    DataResult<List<CustomerListDto>> getAll() throws BusinessException;

    DataResult<Customer> getCustomerById(int customerId);

    DataResult<CustomerListDto> getById(int customerId);

    void checkIfCustomerIdExists(int customerId) throws BusinessException;


}
