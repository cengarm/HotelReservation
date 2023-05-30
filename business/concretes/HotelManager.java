package com.example.hotelReservationSystem.business.concretes;

import com.example.hotelReservationSystem.business.abstracts.HotelService;
import com.example.hotelReservationSystem.core.utilities.enums.HotelStatu;
import com.example.hotelReservationSystem.core.utilities.exceptions.BusinessException;
import com.example.hotelReservationSystem.core.utilities.mapping.ModelMapperService;
import com.example.hotelReservationSystem.core.utilities.messages.BusinessMessages;
import com.example.hotelReservationSystem.core.utilities.result.*;
import com.example.hotelReservationSystem.dataAccess.HotelRepository;
import com.example.hotelReservationSystem.entity.concretes.Hotel;
import com.example.hotelReservationSystem.business.dtos.responses.hotelResponses.HotelListDto;
import com.example.hotelReservationSystem.business.dtos.requests.hotelRequests.CreateHotelRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotelManager implements HotelService {

    // This is constructor injection.
    private final HotelRepository hotelDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public HotelManager(HotelRepository hotelDao, ModelMapperService modelMapperService) {
        this.hotelDao = hotelDao;
        this.modelMapperService = modelMapperService;
    }

    // This method is adding a new hotel to the database.
    @Override
    public Result add(CreateHotelRequest createHotelRequest) throws BusinessException {
        try {
            checkValidEmail(createHotelRequest.getHotelEmail());
            checkByExistsEmail(createHotelRequest.getHotelEmail());
            Hotel hotel = this.modelMapperService.forRequest().map(createHotelRequest, Hotel.class);
            hotel.setRole("HOTEL_MANAGEMENT");
            hotel.setHotelStatu(HotelStatu.WAIT_APPROVAL);
            this.hotelDao.save(hotel);
            Hotel hote1 = this.hotelDao.findByEmail(createHotelRequest.getHotelEmail());
            log.info(BusinessMessages.LogMessages.ADD_OPERATINON_WORK + " HotelManager -> Add Operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.ADD_OPERATINON_NOT_WORK + " HotelManager -> Add Operation");
            return new ErrorResult(e.getMessage());
        }
    }

    // This method is deleting a hotel from the database.
    @Override
    public Result delete(int id) throws BusinessException {
        try {
            this.hotelDao.deleteById(id);
            log.info(BusinessMessages.LogMessages.DELETE_FROM_DATABASE + " HoteManager -> Delete Operation");
            return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
        } catch (Exception e) {
            log.warn(BusinessMessages.LogMessages.DELETE_OPERATINON_NOT_WORK + " HoteManager -> Delete Operation");
            return new ErrorResult(e.getMessage());
        }
    }


    @Override
    public DataResult<List<HotelListDto>> getAll() throws BusinessException {
        List<Hotel> hotels = this.hotelDao.findAll();
        List<HotelListDto> hotelListDtos = hotels.stream().map(hotel -> this.modelMapperService.forDto()
                .map(hotel, HotelListDto.class)).collect(Collectors.toList());
        log.info(BusinessMessages.LogMessages.GET_LIST_WORKED + " HotelManager -> GetAll Operation ");
        return new SuccessDataResult<>(hotelListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public DataResult<HotelListDto> getById(int hotelId) throws BusinessException {
        Hotel hotel = this.hotelDao.getById(hotelId);
        HotelListDto hotelListDto = this.modelMapperService.forDto().map(hotel, HotelListDto.class);
        return new SuccessDataResult<>(hotelListDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override

    public Result updateHolteStatu(HotelStatu hotelStatu, int hotelId) throws BusinessException {
        Hotel hotel = this.hotelDao.getById(hotelId);
        hotel.setHotelStatu(hotelStatu);
        this.hotelDao.save(hotel);
        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public void checkIfHotelStatu(int userId) throws BusinessException {
        Hotel hotel = this.hotelDao.getById(userId);
        if (!hotel.getHotelStatu().equals(HotelStatu.APPROVALED)) {
            throw new BusinessException(BusinessMessages.Hotel.HOTEL_NOT_CONFIRM + userId);
        }
    }


    private void checkByExistsEmail(String email) throws BusinessException {
        log.info(BusinessMessages.LogMessages.CHECKING_DB + "HotelManager -> CheckByExistsEmail Operation");
        if (this.hotelDao.existsByEmail(email)) {
            throw new BusinessException(BusinessMessages.User.USER_EMAIL_NOT_FOUND + email);
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


}
