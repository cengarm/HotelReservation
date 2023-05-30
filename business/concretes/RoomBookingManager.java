package com.example.hotelReservationSystem.business.concretes;

import com.example.hotelReservationSystem.business.abstracts.RoomBookingService;
import com.example.hotelReservationSystem.business.abstracts.RoomRenovationService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.mapping.ModelMapperService;
import com.example.hotelReservationSystem.core.utilities.messages.BusinessMessages;
import com.example.hotelReservationSystem.core.utilities.result.*;
import com.example.hotelReservationSystem.dataAccess.RoomBokingRepository;
import com.example.hotelReservationSystem.dataAccess.RoomRepository;
import com.example.hotelReservationSystem.entity.concretes.Room;
import com.example.hotelReservationSystem.entity.concretes.RoomBooking;
import com.example.hotelReservationSystem.business.dtos.responses.roomBookingResponses.RoomBookingListDto;
import com.example.hotelReservationSystem.business.dtos.requests.roomBookingRequests.CreateRoomBookingRequest;
import com.example.hotelReservationSystem.business.dtos.requests.roomBookingRequests.DeleteRoomBookingRequests;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomBookingManager implements RoomBookingService {

    // This is constructor injection.
    private final RoomBokingRepository roomBokingDao;
    private final RoomRepository roomDao;
    private final RoomRenovationService roomRenovationService;
    private final ModelMapperService modelMapperService;


    @Autowired
    public RoomBookingManager(RoomBokingRepository roomBokingDao, RoomRepository roomDao, RoomRenovationService roomRenovationService, ModelMapperService modelMapperService) {
        this.roomBokingDao = roomBokingDao;
        this.roomDao = roomDao;
        this.roomRenovationService = roomRenovationService;
        this.modelMapperService = modelMapperService;

    }

    /**
     * It adds a new room booking.
     *
     * @param createRoomBookingRequest This is the request object that comes from the client.
     * @return Result
     */
    @Override
    public Result add(CreateRoomBookingRequest createRoomBookingRequest) throws BusinessException {
        try {
            checkAllCommonCrateValidation(createRoomBookingRequest);
            this.roomRenovationService.checkIfRoomId(createRoomBookingRequest.getRoomId());
            Room room = this.roomDao.getById(createRoomBookingRequest.getRoomId());
            RoomBooking roomBooking = this.modelMapperService.forRequest().map(createRoomBookingRequest, RoomBooking.class);
            roomBooking.setTotalPrice(calculateRoomBookingTotalDayPrice(createRoomBookingRequest.getStartDate(), createRoomBookingRequest.getFinishDate(), room.getDailyPrice()));

            this.roomBokingDao.save(roomBooking);
            log.info(BusinessMessages.LogMessages.ADD_OPERATINON_WORK + "RoomBookingManager -> Add Operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.ADD_OPERATINON_NOT_WORK + "RoomBookingManager -> Add Operation");
            return new ErrorResult(e.getMessage());
        }

    }


    @Override
    public Result delete(DeleteRoomBookingRequests deleteRoomBookingRequests) throws BusinessException {
        try {
            checkIfNotRoomBokingIdExists(deleteRoomBookingRequests.getId());
            RoomBooking roomBooking = this.modelMapperService.forRequest().map(deleteRoomBookingRequests, RoomBooking.class);
            this.roomBokingDao.deleteById(deleteRoomBookingRequests.getId());
            log.info(BusinessMessages.LogMessages.DELETE_FROM_DATABASE + "RoomBookingManager -> Delete Operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + deleteRoomBookingRequests.getId());
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.GET_LIST_NOT_WORK + "RoomBookingManager -> Delete Operation");
            return new ErrorResult(e.getMessage());
        }
    }


    @Override
    public DataResult<List<RoomBookingListDto>> getAll() throws BusinessException {

        List<RoomBooking> roomBookings = this.roomBokingDao.findAll();
        List<RoomBookingListDto> roomBookingListDtos = roomBookings.stream().map(roomBooking -> this.modelMapperService.forDto().map(roomBooking, RoomBookingListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(roomBookingListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<RoomBookingListDto> getById(int roomBookinId) throws BusinessException {
        RoomBooking roomBooking = this.roomBokingDao.getById(roomBookinId);
        RoomBookingListDto roomBookingListDto = this.modelMapperService.forDto().map(roomBooking, RoomBookingListDto.class);
        return new SuccessDataResult<>(roomBookingListDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public DataResult<List<RoomBookingListDto>> getAllByRoomBooking_RoomId(int roomId) throws BusinessException {
        List<RoomBooking> roomBookings = this.roomBokingDao.getAllByRoom_Id(roomId);
        List<RoomBookingListDto> roomBookingListDtos = roomBookings.stream().map(roomBooking -> this.modelMapperService.forDto().map(roomBooking, RoomBookingListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(roomBookingListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    // Getting all the room bookings by customer id.
    @Override
    public DataResult<List<RoomBookingListDto>> getAllByCustomer_Id(int customerId) throws BusinessException {
        List<RoomBooking> roomBookings = this.roomBokingDao.getAllByCustomer_UserId(customerId);
        List<RoomBookingListDto> roomBookingListDtos = roomBookings.stream().map(roomBooking -> this.modelMapperService.forDto().map(roomBooking, RoomBookingListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(roomBookingListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public void checkIfNotRoomBokingIdExists(int id) throws BusinessException {
        log.info(BusinessMessages.LogMessages.CHECKING_DB);
    // It checks if the finish date is before the start date.
        if (!this.roomBokingDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.RoomBooking.ROOMBOOKING_ID_NOT_FOUND + id);
        }
    }

    @Override
    public void checkIfFisrtDateBeforeSecondDate(LocalDate startDate, LocalDate finishDate) throws BusinessException {
        if (finishDate.isBefore(startDate) || startDate.equals(finishDate)) {
            throw new BusinessException(BusinessMessages.RoomBooking.FINISH_DATE_CANNOT_BEFORE_START_DATE);
        }
    }


     @Override
    public int getTotalDaysForBooking(LocalDate startDate, LocalDate finishDate) throws BusinessException {
        return (int) ChronoUnit.DAYS.between(startDate, finishDate);
    }


    @Override
    public double calculateRoomBookingTotalDayPrice(LocalDate startDate, LocalDate finishDate, double dailyPrice) throws BusinessException {
        int totalDays = getTotalDaysForBooking(startDate, finishDate);
        double result = totalDays * dailyPrice;
        return result;

    }


    @Override
    public void checkAllCommonCrateValidation(CreateRoomBookingRequest createRoomBookingRequest) throws BusinessException {
        checkIfFisrtDateBeforeSecondDate(createRoomBookingRequest.getStartDate(), createRoomBookingRequest.getFinishDate());
        checkIfRoomAlreadyBookingForCreate(createRoomBookingRequest.getRoomId(), createRoomBookingRequest.getStartDate(), createRoomBookingRequest.getFinishDate());
    }


    @Override
    public void checkIfRoomBookingIdExists(int id) throws BusinessException {
        log.info(BusinessMessages.LogMessages.CHECKING_DB);
        if (!this.roomBokingDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.RoomBooking.ROOMBOOKING_ID_NOT_FOUND + id);
        }
    }


    private void checkIfRoomAlreadyBookingBetweenStartAndFinishDates(RoomBooking roomBooking, LocalDate startDate, LocalDate finishDate) throws BusinessException {
        if ((roomBooking.getStartDate().isAfter(startDate) && (roomBooking.getFinishDate().isBefore(finishDate))) || (roomBooking.getStartDate().equals(startDate) || (roomBooking.getFinishDate().equals(finishDate)))) {
            throw new BusinessException(BusinessMessages.RoomBooking.ROOM_ALREADY_BOOKING_ENTERED_DATES + "startDate: " + startDate + "finishDate: " + finishDate);
        }
    }

    private void checkIfRoomAlreadyBookingOnTheEnteredDate(RoomBooking roomBooking, LocalDate enteredDate) throws BusinessException {
        if (roomBooking.getStartDate().isBefore(enteredDate) && (roomBooking.getFinishDate().isAfter(enteredDate))) {
            throw new BusinessException(BusinessMessages.RoomBooking.ROOM_ALREADY_BOOKING_ENTERED_DATES + enteredDate);
        }
    }

    @Override
    public void checkIfRoomAlreadyBookingForCreate(int roomId, LocalDate startDate, LocalDate finishDate) throws BusinessException {
        List<RoomBooking> roomBookings = this.roomBokingDao.getAllByRoom_Id(roomId);
        if (roomBookings != null) {
            for (RoomBooking roomBooking : roomBookings) {
                checkIfRoomAlreadyBookingOnTheEnteredDate(roomBooking, startDate);
                checkIfRoomAlreadyBookingOnTheEnteredDate(roomBooking, finishDate);
                checkIfRoomAlreadyBookingBetweenStartAndFinishDates(roomBooking, startDate, finishDate);

            }
        }
    }

}
