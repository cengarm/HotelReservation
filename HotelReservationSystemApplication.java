package com.example.hotelReservationSystem;

import com.example.hotelReservationSystem.core.utilities.messages.BusinessMessages;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication

@Slf4j
public class HotelReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelReservationSystemApplication.class, args);
        log.info(BusinessMessages.LogMessages.SYSTEM_STARTED);

    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
