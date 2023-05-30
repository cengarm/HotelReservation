package com.example.hotelReservationSystem.business.concretes;

import com.example.hotelReservationSystem.business.abstracts.CustomerService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.mapping.ModelMapperService;
import com.example.hotelReservationSystem.core.utilities.messages.BusinessMessages;
import com.example.hotelReservationSystem.core.utilities.result.*;
import com.example.hotelReservationSystem.dataAccess.CustomerRepository;
import com.example.hotelReservationSystem.entity.concretes.Customer;
import com.example.hotelReservationSystem.business.dtos.responses.CustomerResponses.CustomerListDto;
import com.example.hotelReservationSystem.business.dtos.requests.customerRequests.CreateCustomerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerManager implements CustomerService {
    // This is a constructor injection.
    private final CustomerRepository customerDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CustomerManager(CustomerRepository customerDao, ModelMapperService modelMapperService) {
        this.customerDao = customerDao;
        this.modelMapperService = modelMapperService;
    }


     @Override
    public Result add(CreateCustomerRequest createCustomerRequest) throws BusinessException {
        try {
            checkValidEmail(createCustomerRequest.getEmail());
            checkIfCustomerEmailExists(createCustomerRequest.getEmail());
            Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);
            customer.setRole("CUSTOMER");
            this.customerDao.save(customer);
            getEmail(createCustomerRequest);
            log.info(BusinessMessages.LogMessages.ADD_OPERATINON_WORK + " CustomerMager -> Add Operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            log.info(BusinessMessages.LogMessages.ADD_OPERATINON_NOT_WORK + " CustomerMager -> Add Operation");
            return new ErrorResult(e.getMessage());
        }
    }


    @Override
    public Result deleteByCustomerId(int id) throws BusinessException {
        try {
            checkIfCustomerIdExists(id);
            this.customerDao.deleteById(id);
            log.info(BusinessMessages.LogMessages.DELETE_FROM_DATABASE + " CustomerMager -> Delete Operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + id);
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.DELETE_OPERATINON_NOT_WORK + " CustomerMager -> Delete Operation");
            return new ErrorResult(e.getMessage());
        }
    }


    @Override
    public DataResult<List<CustomerListDto>> getAll() throws BusinessException {
        List<Customer> customers = this.customerDao.findAll();
        List<CustomerListDto> customerListDtos = customers.stream().map(customer -> this.modelMapperService.forDto().map(customer, CustomerListDto.class)).collect(Collectors.toList());
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " CustomerMager -> GetAll Operation");
        return new SuccessDataResult<>(customerListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public void checkIfCustomerIdExists(int customerId) throws BusinessException {
        log.info(BusinessMessages.LogMessages.CHECKING_DB + " CustomerMager -> CheckIfCustomerIdExists Operation");

        if (!this.customerDao.existsById(customerId)) {
            throw new BusinessException(BusinessMessages.CustomerMessages.CUSTOMER_ID_NOT_FOUND + customerId);
        }
    }


     @Override
    public DataResult<Customer> getCustomerById(int customerId) {
        return new SuccessDataResult<>(this.customerDao.getById(customerId), BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CustomerListDto> getById(int customerId) {
        Customer customer = this.customerDao.getById(customerId);
        CustomerListDto customerListDto = this.modelMapperService.forDto().map(customer, CustomerListDto.class);
        return new SuccessDataResult<>(customerListDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    public void checkIfCustomerEmailExists(String email) throws BusinessException {
        log.info(BusinessMessages.LogMessages.CHECKING_DB + " CustomerMager -> CheckIfCustomerEmailExists Operation");
        if (this.customerDao.existsByEmail(email)) {
            throw new BusinessException(BusinessMessages.CustomerMessages.CUSTOMER_EMAIL_ALREAY_EXISTS + email);
        }
    }


    private void checkValidEmail(String email) throws BusinessException {
        boolean isEmail;
        String emailRegex = "^[a-zA-Z]+[a-zA-Z0-9]*[- . + _]?[a-zA-Z0-9]+[@]{1}[a-z0-9]+[.]{1}[a-z]+[.]?[a-z]+$";
        Pattern patternObject = Pattern.compile(emailRegex);
        if (email == null) {
            isEmail = false;
        }
        Matcher matcherObject = patternObject.matcher(email);
        isEmail = matcherObject.matches();
        if (!isEmail) {
            throw new BusinessException(BusinessMessages.CustomerMessages.CUSTOMER_EMAIL_INVALID + email);
        }
    }

    private void getEmail(CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.customerDao.getByEmail(createCustomerRequest.getEmail());

    }
}
