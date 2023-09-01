package com.ms.assignment.controller;

import com.ms.assignment.dto.ParkingDtlDto;
import com.ms.assignment.service.CarParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarParkingController {

    private final CarParkingService service;

    @GetMapping(value = "/bookparkingslot")
    public ParkingDtlDto bookParkingSlot(@RequestParam String registration) {
        return service.bookParkingSlot(registration);
    }

    @GetMapping(value = "/calculatecharges")
    public ParkingDtlDto calculateCharges(String registration) {
        return service.calculateCharges(registration);
    }
}
