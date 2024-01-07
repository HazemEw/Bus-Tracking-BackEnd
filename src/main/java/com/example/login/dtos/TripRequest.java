package com.example.login.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripRequest {
    StationDto stationDto;
    private  double currentLatitude;
    private double currentLongitude;
}
