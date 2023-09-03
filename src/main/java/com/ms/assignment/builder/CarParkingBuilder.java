package com.ms.assignment.builder;

import com.ms.assignment.dto.ParkingDtlDto;
import com.ms.assignment.entity.Car;
import com.ms.assignment.entity.ParkingDetails;
import java.time.LocalDateTime;

public class CarParkingBuilder {
    public static ParkingDetails buildParkingDetails(String registration) {
        return ParkingDetails.builder()
                .car(Car.builder()
                        .registration(registration)
                        .build())
                .inTime(LocalDateTime.now())
                .isActive(true).build();
    }
    public static ParkingDtlDto buildParkingDto(
            String registration, String message, Double charges) {
        return ParkingDtlDto.builder()
                .registration(registration)
                .message(message)
                .charges(charges). build();
    }
}
