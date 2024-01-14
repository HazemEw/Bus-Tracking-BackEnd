package com.example.login.service;

import com.example.login.dtos.StationDto;
import com.example.login.dtos.TripRequest;
import com.example.login.dtos.TripResponse;

import java.util.List;

public interface StationService {
    StationDto getStation(Long stationTd);
    List<StationDto> getStations();
    StationDto addStation(StationDto stationDto);
    void deleteStation(Long stationTd);

    StationDto findNearestStation(double userLat, double userLng);

    List<StationDto> findNearestStations(double userLat, double userLng);

    List<StationDto> getStationsByCity(String cityName);

    TripResponse findNearestStationToTravel(TripRequest tripRequest);

    StationDto updateStation(Long id, StationDto stationDto);
}
