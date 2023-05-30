package com.example.hotelReservationSystem.business.abstracts;

import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.systemManagementResponses.SystemManagementListDtos;
import com.example.hotelReservationSystem.business.dtos.requests.systemManagementRequests.CreateSystemManagementRequest;

import java.util.List;

public interface SystemManagementService {

    // A service interface.
    Result add(CreateSystemManagementRequest createSystemManagementRequest) throws BusinessException;

    Result delete(int systemManagementId) throws BusinessException;

    DataResult<List<SystemManagementListDtos>> getAll() throws BusinessException;

    DataResult<SystemManagementListDtos> getById(int systemManagementId) throws BusinessException;

    void checkIfExistsById(int systemManagementId) throws BusinessException;


}
