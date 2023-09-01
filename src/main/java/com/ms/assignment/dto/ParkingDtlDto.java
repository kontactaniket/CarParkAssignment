package com.ms.assignment.dto;

import lombok.Builder;
import lombok.Data;

@Data@Builder
public class ParkingDtlDto {
    private String registration;
    private String message;
    private Double charges;
}
