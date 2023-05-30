package com.example.hotelReservationSystem.business.concretes;

import com.example.hotelReservationSystem.business.abstracts.HotelService;
import com.example.hotelReservationSystem.business.abstracts.RoomBookingService;
import com.example.hotelReservationSystem.business.abstracts.RoomService;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.mapping.ModelMapperService;
import com.example.hotelReservationSystem.core.utilities.messages.BusinessMessages;
import com.example.hotelReservationSystem.core.utilities.result.*;
import com.example.hotelReservationSystem.dataAccess.RoomBokingRepository;
import com.example.hotelReservationSystem.dataAccess.RoomRepository;
import com.example.hotelReservationSystem.entity.concretes.Room;
import com.example.hotelReservationSystem.entity.concretes.RoomBooking;
import com.example.hotelReservationSystem.business.dtos.responses.roomResponses.RoomListDto;
import com.example.hotelReservationSystem.business.dtos.requests.roomRequests.CreateRoomRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomManager implements RoomService {

    private final RoomRepository roomDao;
    private final RoomBookingService roomBookingService;
    private final ModelMapperService modelMapperService;
    private final RoomBokingRepository roomBookingDao;

    private final HotelService hotelService;


    @Autowired
    public RoomManager(RoomRepository roomDao, RoomBookingService roomBookingService, ModelMapperService modelMapperService, RoomBokingRepository roomBookingDao, HotelService hotelService) {
        this.roomDao = roomDao;
        this.roomBookingService = roomBookingService;
        this.modelMapperService = modelMapperService;
        this.roomBookingDao = roomBookingDao;
        this.hotelService = hotelService;
    }


    @Override
    public Result add(CreateRoomRequest createRoomRequest) throws BusinessException {

        try {
            Room room = this.modelMapperService.forRequest().map(createRoomRequest, Room.class);
            this.hotelService.checkIfHotelStatu(createRoomRequest.getHotelUserId());
            this.roomDao.save(room);
            log.info(BusinessMessages.LogMessages.ADD_OPERATINON_WORK + " RoomManager -> add operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);

        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.ADD_OPERATINON_NOT_WORK + " RoomManager -> add operation");
            return new ErrorResult(e.getMessage());
        }
    }

    @Override
    public Result deleteRoomByID(int roomid) throws BusinessException {
        try {
            checkIsRoomIdExists(roomid);
            this.roomDao.deleteById(roomid);
            log.info(BusinessMessages.LogMessages.DELETE_FROM_DATABASE + " RoomManager -> deleteRoomById operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.DELETE_OPERATINON_NOT_WORK + " RoomManager -> deleteRoomById operation");
            return new ErrorResult(e.getMessage());
        }
    }

    @Override
    public DataResult<List<RoomListDto>> getAll() throws BusinessException {
        List<Room> rooms = this.roomDao.findAll();
        List<RoomListDto> roomListDtos = rooms.stream().map(room -> this.modelMapperService.forDto().map(room, RoomListDto.class)).collect(Collectors.toList());
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " RoomManager -> getAll operation");
        return new SuccessDataResult<>(roomListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public DataResult<RoomListDto> getById(int roomId) throws BusinessException {
        checkIsRoomIdExists(roomId);
        Room room = this.roomDao.getById(roomId);
        RoomListDto roomListDto = this.modelMapperService.forDto().map(room, RoomListDto.class);
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " RoomManager -> getById operation");
        return new SuccessDataResult<>(roomListDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public DataResult<List<RoomListDto>> findByDailyPriceLessThenEqual(double dailyPrice) {

        List<Room> rooms = this.roomDao.findByDailyPriceLessThanEqual(dailyPrice);
        List<RoomListDto> response = rooms.stream().map(room -> this.modelMapperService.forDto().map(room, RoomListDto.class)).collect(Collectors.toList());
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " RoomManager -> findByDailyPriceLessThenEqual operation");
        return new SuccessDataResult<>(response, BusinessMessages.Room.ROOM_LISTED_BY_LESS_THEN_EQUAL + dailyPrice);
    }

    @Override
    public DataResult<List<RoomListDto>> getAllPagedRoom(int pageNo, int pageSize) throws BusinessException {
        checkIfPageNoAndPageSizeValid(pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Room> rooms = this.roomDao.findAll(pageable).getContent();
        List<RoomListDto> roomListDtos = rooms.stream().map(room -> this.modelMapperService.forDto().map(room, RoomListDto.class)).collect(Collectors.toList());
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " RoomManager -> getAllPagedRoom operation");
        return new SuccessDataResult<>(roomListDtos, BusinessMessages.Room.ALL_ROOMS_PAGED);
    }


    @Override
    public DataResult<List<RoomListDto>> getAllSortedRoom(int sort) throws BusinessException {
        Sort sort1 = selectSortedType(sort);

        List<Room> rooms = this.roomDao.findAll(sort1);
        List<RoomListDto> roomListDtos = rooms.stream().map(room -> this.modelMapperService.forDto().map(room, RoomListDto.class)).collect(Collectors.toList());
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " RoomManager -> getAllSortedRoom operation");
        return new SuccessDataResult<>(roomListDtos, BusinessMessages.Room.ALL_ROOMS_SORTED);
    }

    @Override
    public void checkIsRoomIdExists(int roomid) throws BusinessException {
        if (!this.roomDao.existsById(roomid)) {
            log.info(BusinessMessages.LogMessages.CHECKING_DB + "RoomManager -> checkIsRoomIdExists operation");
            throw new BusinessException(BusinessMessages.Room.ROOM_ID_NOT_FOUND + roomid);
        }
    }

    @Override
    public double getDailyPriceByRoomId(int roomId) throws BusinessException {
        Room room = this.roomDao.getById(roomId);
        return room.getDailyPrice();
    }


    @Override
    public DataResult<List<RoomListDto>> getAllAvailableRooms(LocalDate startDate, LocalDate finishDate) throws BusinessException {
        try {
            checkIfFisrtDateBeforeSecondDate(startDate, finishDate);
            List<Room> rooms = this.roomDao.findAll();
            List<Room> availableRooms = new ArrayList<>();
            int x = 0;
            for (Room room : rooms) {
                log.info(BusinessMessages.LogMessages.CHECK_OF_DATE + room.getId());
                List<RoomBooking> allBookings = this.roomBookingDao.getAllByRoom_Id(room.getId());
                for (RoomBooking booking : allBookings) {
                    if (checkIfRoomAlreadyBookingOnTheEnteredDate(booking, startDate) &&
                            checkIfRoomAlreadyBookingOnTheEnteredDate(booking, finishDate) &&
                            checkIfRoomAlreadyBookingBetweenStartAndFinishDates(booking, startDate, finishDate)){
                    } else {
                        x++;
                    }
                }
                if (x == 0) {
                    availableRooms.add(room);
                }
                x = 0;
            }
            List<RoomListDto> roomListDtos = availableRooms.stream().map(room -> this.modelMapperService.forDto().map(room, RoomListDto.class)).collect(Collectors.toList());
            log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " Room Manager -> getAllAvailableRooms");
            return new SuccessDataResult<>(roomListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    private boolean checkIfRoomAlreadyBookingBetweenStartAndFinishDates(RoomBooking roomBooking, LocalDate startDate, LocalDate finishDate) throws BusinessException {
        if ((roomBooking.getStartDate().isAfter(startDate) && (roomBooking.getFinishDate().isBefore(finishDate))) || (roomBooking.getStartDate().equals(startDate) || (roomBooking.getFinishDate().equals(finishDate)))) {
            return false;
        } else {
            return true;
        }
    }


    private boolean checkIfRoomAlreadyBookingOnTheEnteredDate(RoomBooking roomBooking, LocalDate enteredDate) throws BusinessException {
        if (roomBooking.getStartDate().isBefore(enteredDate) && (roomBooking.getFinishDate().isAfter(enteredDate))) {
            return false;
        } else {
            return true;
        }
    }



    private void checkIfPageNoAndPageSizeValid(int pageNo, int pageSize) throws BusinessException {
        if (pageNo <= 0 || pageSize <= 0) {
            throw new BusinessException(BusinessMessages.Room.PAGE_NO_OR_PAGE_SIZE_NOT_VALID + "pageNO: " + pageNo + "PageSiz: " + pageSize);
        }
    }


    private void checkIfFisrtDateBeforeSecondDate(LocalDate startDate, LocalDate finishDate) throws BusinessException {
        if (finishDate.isBefore(startDate) || startDate.equals(finishDate)) {
            throw new BusinessException(BusinessMessages.RoomBooking.FINISH_DATE_CANNOT_BEFORE_START_DATE);
        }
    }


    Sort selectSortedType(int sort) {
        if (sort == 1) {
            return Sort.by(Sort.Direction.ASC, "dailyPrice");
        } else if (sort == 0) {
            return Sort.by(Sort.Direction.DESC, "dailyPrice");
        } else {
            return Sort.by(Sort.Direction.DESC, "dailyPrice");
        }
    }


}
