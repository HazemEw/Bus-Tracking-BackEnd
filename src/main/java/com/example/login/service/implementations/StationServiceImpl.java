package com.example.login.service.implementations;

import com.example.login.dtos.StationDto;
import com.example.login.exceptions.DuplicateException;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.StationMapper;
import com.example.login.model.RouteStation;
import com.example.login.model.Station;
import com.example.login.repo.StationRepo;
import com.example.login.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepo stationRepo;

    private final StationMapper stationMapper;

    public StationDto addStation(StationDto stationDto) {
        if (!stationRepo.findByName(stationDto.getName()).isPresent()) {
            Station station = stationMapper.mapToStation(stationDto);
            station.setRoutes(new ArrayList<RouteStation>());
            stationRepo.save(station);
            return stationDto;
        } else
            throw new DuplicateException(stationDto.getName());
    }

    public List<StationDto> getStations() {
        List<StationDto> stationDtoList= new ArrayList<>();
        stationRepo.findAll().forEach(station -> stationDtoList.add(
                stationMapper.mapToDto(station)
        ));
        return stationDtoList;
    }

    public StationDto getStation(Long id){
        Station station = stationRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Station","id",id)
        );
        StationDto stationDto = stationMapper.mapToDto(station);
        return stationDto;
    }

    public void deleteStation(Long id) {
        Station station = stationRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Station","id",id)
        );
        stationRepo.delete(station);
    }

    public StationDto findNearestStation(double userLat, double userLng) {
        Station station = stationRepo.findAll().stream()
                .min((station1, station2) -> Double.compare(
                        calculateDistance(userLat, userLng, station1.getLatitude(), station1.getLongitude()),
                        calculateDistance(userLat, userLng, station2.getLatitude(), station2.getLongitude())))
                .orElseThrow(()->new ResourceNotFoundException());
        return stationMapper.mapToDto(station);
    }

    @Override
    public List<StationDto> findNearestStations(double userLat, double userLng) {
        List<StationDto> nearestStations = new ArrayList<>();
        stationRepo.findAll().stream().forEach(station -> {
            if (calculateDistance(userLat,userLng,station.getLatitude(),station.getLongitude()) <=11500)
                nearestStations.add(stationMapper.mapToDto(station));
        });
        return nearestStations;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000;
    }
}
