package com.ms.assignment.controller;

import com.ms.assignment.dto.ParkingDtlDto;
import com.ms.assignment.service.CarParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarParkingController {

    private final CarParkingService service;

    @GetMapping(value = "/bookparkingslot")
    public ResponseEntity<ParkingDtlDto> bookParkingSlot(@RequestParam String registration) {
        // Different response code can be sent depending on client.
        return ResponseEntity.ok(service.bookParkingSlot(registration));
    }

    @GetMapping(value = "/calculatecharges")
    public ResponseEntity<ParkingDtlDto> calculateCharges(String registration) {
        // Different response code can be sent depending on client.
        return ResponseEntity.ok(service.calculateCharges(registration));
    }
}
