package com.example.hotelReservationSystem.business.abstracts;

import com.example.hotelReservationSystem.core.utilities.enums.HotelStatu;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.hotelResponses.HotelListDto;
import com.example.hotelReservationSystem.business.dtos.requests.hotelRequests.CreateHotelRequest;

import java.util.List;

public interface HotelService {
// A service interface.


    Result add(CreateHotelRequest createHotelRequest) throws BusinessException;

    Result delete(int id) throws BusinessException;


    Result updateHolteStatu(HotelStatu hotelStatu, int hotelId) throws BusinessException;

    DataResult<List<HotelListDto>> getAll() throws BusinessException;

    DataResult<HotelListDto> getById(int hotelId) throws BusinessException;

    void checkIfHotelStatu(int userId) throws BusinessException;

}
