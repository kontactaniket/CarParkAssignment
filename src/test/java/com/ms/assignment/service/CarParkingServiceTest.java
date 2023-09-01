package com.ms.assignment.service;

import com.ms.assignment.dto.ParkingDtlDto;
import com.ms.assignment.entity.Car;
import com.ms.assignment.entity.ParkingDetails;
import com.ms.assignment.repository.ParkingDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarParkingServiceTest {
    @Mock
    ParkingDetailRepository repo;
    @InjectMocks
    CarParkingService service;

    @BeforeEach
    public void setup() {
        service.setParkingCharges(2);
        service.setTotalParking(100);
    }

    @Test
    void _test_bookParkingSlot_valid1() {
        when(repo.getOccupiedSpaces()).thenReturn(99);
        when(repo.getParkingDetails(any(),any())).thenReturn(null);
        when(repo.save(any())).thenReturn(buildParkingDetails(0));
        ParkingDtlDto dto = service.bookParkingSlot("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "Parking allotted for car: AB23CDE");
        assertEquals(dto.getCharges(), 0.0);
    }

    @Test
    void _test_bookParkingSlot_noSpaceAvailable() {
        when(repo.getOccupiedSpaces()).thenReturn(100);
        ParkingDtlDto dto = service.bookParkingSlot("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "Parking full!!!");
        assertEquals(dto.getCharges(), 0.0);
    }

    @Test
    void _test_bookParkingSlot_addingSameCar() {
        when(repo.getOccupiedSpaces()).thenReturn(99);
        when(repo.getParkingDetails(any(),any())).thenReturn(ParkingDetails.builder().build());
        ParkingDtlDto dto = service.bookParkingSlot("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "Car already parked and can not be added again!!!");
        assertEquals(dto.getCharges(), 0.0);
    }

    @Test
    void _test_bookParkingSlot_exceptionOnSave() {
        when(repo.getOccupiedSpaces()).thenReturn(99);
        when(repo.getParkingDetails(any(),any())).thenReturn(null);
        when(repo.save(any())).thenThrow(new IllegalArgumentException());
        ParkingDtlDto dto = service.bookParkingSlot("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "Error occurred while allotting parking!!!");
        assertEquals(dto.getCharges(), 0.0);
    }

    @Test
    void _test_bookParkingSlot_nullRegistration() {
        ParkingDtlDto dto = service.bookParkingSlot(null);
        assertNull(dto.getRegistration());
        assertEquals(dto.getMessage(), "Registration can not be empty!!!");
        assertEquals(dto.getCharges(), 0.0);
    }

    @Test
    void _test_calculateCharges_valid1() {
        ParkingDetails dtl = buildParkingDetails(125*60);
        when(repo.getParkingDetails(any(), any())).thenReturn(dtl);
        when(repo.save(any())).thenReturn(dtl);
        ParkingDtlDto dto =service.calculateCharges("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "Parking charges for car: AB23CDE is: £6.0");
        assertEquals(dto.getCharges(), 6.0);
    }

    @Test
    void _test_calculateCharges_valid2() {
        ParkingDetails dtl = buildParkingDetails(10);
        when(repo.getParkingDetails(any(), any())).thenReturn(buildParkingDetails(10));
        when(repo.save(any())).thenReturn(dtl);
        ParkingDtlDto dto =service.calculateCharges("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "Parking charges for car: AB23CDE is: £2.0");
        assertEquals(dto.getCharges(), 2.0);
    }

    @Test
    void _test_calculateCharges_valid3() {
        ParkingDetails dtl = buildParkingDetails(60*60*25);
        when(repo.getParkingDetails(any(), any())).thenReturn(buildParkingDetails(60*60*25));
        when(repo.save(any())).thenReturn(dtl);
        ParkingDtlDto dto =service.calculateCharges("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "Parking charges for car: AB23CDE is: £50.0");
        assertEquals(dto.getCharges(), 50.0);
    }

    @Test
    void _test_calculateCharges_carNotParked() {
        when(repo.getParkingDetails(any(), any())).thenReturn(null);
        ParkingDtlDto dto =service.calculateCharges("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "No car is parked with the registration: AB23CDE");
        assertEquals(dto.getCharges(), 0.0);
    }

    @Test
    void _test_calculateCharges_exceptionOnSave() {
        when(repo.getParkingDetails(any(), any())).thenReturn(buildParkingDetails(60*60*25));
        ParkingDtlDto dto =service.calculateCharges("AB23CDE");
        assertEquals(dto.getRegistration(), "AB23CDE");
        assertEquals(dto.getMessage(), "Error occurred while calculating charges. Please retry!!!");
        assertEquals(dto.getCharges(), 0.0);
    }

    private ParkingDetails buildParkingDetails(int seconds) {
        return ParkingDetails.builder()
                .car(Car.builder().registration("AB23CDE").build())
                .inTime(LocalDateTime.now().minus(seconds, ChronoUnit.SECONDS))
                .isActive(true)
                .build();
    }
}
