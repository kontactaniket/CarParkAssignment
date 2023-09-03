package com.ms.assignment.service;

import com.ms.assignment.builder.CarParkingBuilder;
import com.ms.assignment.dto.ParkingDtlDto;
import com.ms.assignment.entity.ParkingDetails;
import com.ms.assignment.repository.ParkingDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static com.ms.assignment.builder.CarParkingBuilder.buildParkingDto;

@Service
@RequiredArgsConstructor
public class CarParkingService {
    private final ParkingDetailRepository detailsRepo;
    Lock lock = new ReentrantLock();
    @Value("${spring.carparking.totalparkingslots:100}")
    private Integer totalParking;

    @Value("${spring.carparking.hourcharges:2}")
    private Integer parkingCharges;
    public ParkingDtlDto bookParkingSlot(String registration) {
        try {
            lock.lock();
            if(registration==null) {
                return buildParkingDto(registration
                        , "Registration can not be empty!!!"
                        , 0.0);
            }
            int temp = detailsRepo.getOccupiedSpaces();
            if(temp>=totalParking) {
                return buildParkingDto(
                        registration, "Parking full!!!", 0.0);
            }
            if(detailsRepo.getParkingDetails(registration, true)!=null){
                return buildParkingDto(registration
                        , "Car already parked and can not be added again!!!"
                        , 0.0);
            }
            ParkingDetails detail = detailsRepo
                    .save(CarParkingBuilder.buildParkingDetails(registration));
            return buildParkingDto(detail.getCar().getRegistration()
                    , "Parking allotted for car: "+detail.getCar().getRegistration()
                    , 0.0);
        } catch (IllegalArgumentException ex) {
            // LOG EXCEPTION
            return buildParkingDto(registration
                    , "Error occurred while allotting parking!!!"
                    , 0.0);
        } finally {
            lock.unlock();
        }
    }
    public ParkingDtlDto calculateCharges(String registration) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            ParkingDetails detail = detailsRepo.getParkingDetails(registration, true);
            if(detail == null) {
                return buildParkingDto(registration
                        , "No car is parked with the registration: "+registration
                        , 0.0);
            }
            long seconds = detail.getInTime().until(currentTime, ChronoUnit.SECONDS);
            double charges = seconds<=60?parkingCharges:(seconds/60)%60==0?
                    ((seconds/60)/60)*parkingCharges:
                    (((seconds/60)/60)*parkingCharges)+parkingCharges;
            detail.setOutTime(currentTime);
            detail.setActive(false);
            detail = detailsRepo.save(detail);
            return buildParkingDto(detail.getCar().getRegistration()
                    , String.format("%s %s %s%s"
                            ,"Parking charges for car:"
                            , detail.getCar().getRegistration()
                            , "is: £"
                            , charges)
                    , charges);
        } catch (RuntimeException ex) {
            // LOG EXCEPTION
            return buildParkingDto(registration
                    , "Error occurred while calculating charges. Please retry!!!"
                    , 0.0);
        }
    }
    public void setTotalParking(Integer totalParking) {this.totalParking = totalParking;}
    public void setParkingCharges(Integer parkingCharges) {this.parkingCharges = parkingCharges;}
}