package com.example.hotelReservationSystem.wepApi.controllers;

import com.example.hotelReservationSystem.business.abstracts.RoomRenovationService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.result.DataResult;
import com.example.hotelReservationSystem.core.utilities.result.Result;
import com.example.hotelReservationSystem.business.dtos.responses.roomRenovationResponses.RoomRenovationListDto;
import com.example.hotelReservationSystem.business.dtos.requests.roomRenovationRequests.CreateRoomRenovation;
import com.example.hotelReservationSystem.business.dtos.requests.roomRenovationRequests.DeleteRoomRenovationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roomRenovation")
public class RoomRenovationController {

    // This is a constructor injection.
    private final RoomRenovationService roomRenovationService;

    @Autowired
    public RoomRenovationController(RoomRenovationService roomRenovationService) {
        this.roomRenovationService = roomRenovationService;
    }

    // This is a controller class.
    @PostMapping("/add")
    public Result add(CreateRoomRenovation createRoomRenovation) throws BusinessException {
        return this.roomRenovationService.add(createRoomRenovation);
    }

    @DeleteMapping("/delete")
    public Result delete(DeleteRoomRenovationRequest deleteRoomRenovationRequest) throws BusinessException {
        return this.roomRenovationService.delete(deleteRoomRenovationRequest);
    }

    @GetMapping("/getAll")
    public DataResult<List<RoomRenovationListDto>> getAll() throws BusinessException {
        return this.roomRenovationService.getAll();
    }
}
