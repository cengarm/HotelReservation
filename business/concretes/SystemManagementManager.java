package com.example.hotelReservationSystem.business.concretes;

import com.example.hotelReservationSystem.business.abstracts.SystemManagementService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.mapping.ModelMapperService;
import com.example.hotelReservationSystem.core.utilities.messages.BusinessMessages;
import com.example.hotelReservationSystem.core.utilities.result.*;
import com.example.hotelReservationSystem.dataAccess.SystemManagementRepository;
import com.example.hotelReservationSystem.entity.concretes.SystemManagement;
import com.example.hotelReservationSystem.business.dtos.responses.systemManagementResponses.SystemManagementListDtos;
import com.example.hotelReservationSystem.business.dtos.requests.systemManagementRequests.CreateSystemManagementRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SystemManagementManager implements SystemManagementService {

    private final SystemManagementRepository systemManagementRepository;
    private final ModelMapperService modelMapperService;

    @Autowired
    public SystemManagementManager(SystemManagementRepository systemManagementRepository, ModelMapperService modelMapperService) {
        this.systemManagementRepository = systemManagementRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override


    public Result add(CreateSystemManagementRequest createSystemManagementRequest) throws BusinessException {
        try {
            checkValidEmail(createSystemManagementRequest.getEmail());
            checkIfExistByEmail(createSystemManagementRequest.getEmail());
            SystemManagement systemManagement = this.modelMapperService.forRequest().map(createSystemManagementRequest, SystemManagement.class);
            systemManagement.setRole("SYSTEM_MANAGEMENT");
            this.systemManagementRepository.save(systemManagement);
            log.info(BusinessMessages.LogMessages.ADD_OPERATINON_WORK + " SytemManagementManager -> Add Operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.ADD_OPERATINON_NOT_WORK + " SytemManagementManager -> Add Operation");
            return new ErrorResult(e.getMessage());
        }

    }

    @Override
    public Result delete(int systemManagementId) throws BusinessException {
        try {
            checkIfExistsById(systemManagementId);
            this.systemManagementRepository.deleteById(systemManagementId);
            log.info(BusinessMessages.LogMessages.DELETE_OPERATINON_WORK + " SytemManagementManager -> Delete Operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.DELETE_OPERATINON_NOT_WORK + " SytemManagementManager -> Delete Operation");
            return new ErrorResult(e.getMessage());
        }
    }


    @Override
    public DataResult<List<SystemManagementListDtos>> getAll() throws BusinessException {
        List<SystemManagement> systemManagements = this.systemManagementRepository.findAll();
        List<SystemManagementListDtos> systemManagementListDtos = systemManagements.stream().map(systemManagement -> this.modelMapperService.forDto().map(systemManagements, SystemManagementListDtos.class)).collect(Collectors.toList());
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " SytemManagementManager -> GetAll Operation");
        return new SuccessDataResult<>(systemManagementListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<SystemManagementListDtos> getById(int systemManagementId) throws BusinessException {
        SystemManagement systemManagement = this.systemManagementRepository.getById(systemManagementId);
        SystemManagementListDtos systemManagementListDtos = this.modelMapperService.forDto().map(systemManagement, SystemManagementListDtos.class);
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " SytemManagementManager -> GetById Operation");
        return new SuccessDataResult<>(systemManagementListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public void checkIfExistsById(int systemManagementId) throws BusinessException {
        log.info(BusinessMessages.LogMessages.CHECKING_DB + " SytemManagementManager -> checkIfExistsById Operation");
        if (!this.systemManagementRepository.existsById(systemManagementId)) {
            throw new BusinessException(BusinessMessages.SystemManagementMessages.SYTEM_ID_NOT_FOUND);
        }
    }

    private void checkIfExistByEmail(String email) throws BusinessException {
        log.info(BusinessMessages.LogMessages.CHECKING_DB + " SytemManagementManager -> checkIfExistByEmail Operation");
        if (this.systemManagementRepository.existsByEmail(email)) {
            throw new BusinessException(BusinessMessages.User.USER_EMAIL_NOT_FOUND);
        }
    }

    // Checking if the email is valid or not.
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
}
