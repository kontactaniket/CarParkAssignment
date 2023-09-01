package com.ms.assignment.repository;

import com.ms.assignment.entity.ParkingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingDetailRepository extends JpaRepository<ParkingDetails, Integer> {
    @Query(value = "select count(p.id) from ParkingDetails p where p.isActive = true")
    Integer getOccupiedSpaces();
    @Query(value = "select p from ParkingDetails p where p.car.registration = :registration and p.isActive = :isActive")
    ParkingDetails getParkingDetails(String registration, Boolean isActive);
}
