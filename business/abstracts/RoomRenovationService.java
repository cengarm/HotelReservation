package com.example.hotelReservationSystem.business.abstracts;

import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.roomRenovationResponses.RoomRenovationListDto;
import com.example.hotelReservationSystem.business.dtos.requests.roomRenovationRequests.CreateRoomRenovation;
import com.example.hotelReservationSystem.business.dtos.requests.roomRenovationRequests.DeleteRoomRenovationRequest;

import java.util.List;

public interface RoomRenovationService {
    // A service interface.
    Result add(CreateRoomRenovation createRoomRenovation) throws BusinessException;

    Result delete(DeleteRoomRenovationRequest deleteRoomRenovationRequest) throws BusinessException;

    DataResult<List<RoomRenovationListDto>> getAll() throws BusinessException;

    void checkIfRoomId(int roomId) throws BusinessException;
}
