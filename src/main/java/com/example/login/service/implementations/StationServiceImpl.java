package com.example.login.service.implementations;

import com.example.login.dtos.StationDto;
import com.example.login.dtos.TripRequest;
import com.example.login.dtos.TripResponse;
import com.example.login.exceptions.CustomException;
import com.example.login.exceptions.DuplicateException;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.RouteMapper;
import com.example.login.mapper.StationMapper;
import com.example.login.model.Route;
import com.example.login.model.RouteStation;
import com.example.login.model.Station;
import com.example.login.repo.RouteRepo;
import com.example.login.repo.StationRepo;
import com.example.login.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepo stationRepo;

    private final StationMapper stationMapper;

    private final RouteRepo routeRepo;


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
        List<StationDto> stationDtoList = new ArrayList<>();
        stationRepo.findAll().forEach(station -> stationDtoList.add(
                stationMapper.mapToDto(station)
        ));
        return stationDtoList;
    }

    public StationDto getStation(Long id) {
        Station station = stationRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Station", "id", id)
        );
        StationDto stationDto = stationMapper.mapToDto(station);
        return stationDto;
    }

    public void deleteStation(Long id) {
        Station station = stationRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Station", "id", id)
        );
        stationRepo.delete(station);
    }

    public StationDto findNearestStation(double userLat, double userLng) {
        Station station = stationRepo.findAll().stream()
                .min((station1, station2) -> Double.compare(
                        calculateDistance(userLat, userLng, station1.getLatitude(), station1.getLongitude()),
                        calculateDistance(userLat, userLng, station2.getLatitude(), station2.getLongitude())))
                .orElseThrow(() -> new ResourceNotFoundException());
        return stationMapper.mapToDto(station);
    }

    public Station findNearestStationInRoute(double userLat, double userLng, Route route) {
        List<RouteStation> stations = new ArrayList<>();
        stations = route.getStations();
        List<Station> stationList = new ArrayList<>();
        stations.stream().forEach(routeStation -> {
            stationList.add(routeStation.getStation());
        });

        Station station = stationList.stream()
                .min((station1, station2) -> Double.compare(
                        calculateDistance(userLat, userLng, station1.getLatitude(), station1.getLongitude()),
                        calculateDistance(userLat, userLng, station2.getLatitude(), station2.getLongitude())))
                .orElseThrow(() -> new ResourceNotFoundException());
        return station;
    }

    public Station findNearestStationInStationList(double userLat, double userLng, List<Station> stationList) {
        Station station = stationList.stream()
                .min((station1, station2) -> Double.compare(
                        calculateDistance(userLat, userLng, station1.getLatitude(), station1.getLongitude()),
                        calculateDistance(userLat, userLng, station2.getLatitude(), station2.getLongitude())))
                .orElseThrow(() -> new ResourceNotFoundException());
        return station;
    }

    @Override
    public List<StationDto> findNearestStations(double userLat, double userLng) {
        List<StationDto> nearestStations = new ArrayList<>();
        stationRepo.findAll().stream().forEach(station -> {
            if (calculateDistance(userLat, userLng, station.getLatitude(), station.getLongitude()) <= 11500)
                nearestStations.add(stationMapper.mapToDto(station));
        });
        return nearestStations;
    }

    @Override
    public List<StationDto> getStationsByCity(String cityName) {
        List<StationDto> stationDtoList = new ArrayList<>();
        stationRepo.findByCity(cityName).stream().forEach(station -> {
            stationDtoList.add(stationMapper.mapToDto(station));
        });
        if (stationDtoList.isEmpty())
            throw new CustomException("No Station in : " + cityName, HttpStatus.BAD_REQUEST);
        else
            return stationDtoList;
    }

    @Override
    public TripResponse findNearestStationToTravel(TripRequest tripRequest) {
        TripResponse tripResponse = new TripResponse();
        List<StationDto> stationDtoList = new ArrayList<>();
        Station destinationStation = stationRepo.findByName(tripRequest.getStationDto().getName()).get();
        List<Route> routeList = new ArrayList<>();
        destinationStation.getRoutes().stream().forEach(routeStation -> {
            routeList.add(routeStation.getRoute());

        });

        List<Station> allStation = new ArrayList<>();
        for (int i = 0; i < routeList.size(); i++) {
            routeList.get(i).getStations().stream().forEach(routeStation -> {
                allStation.add(routeStation.getStation());
            });
        }
        Station nearestStation = findNearestStationInStationList(tripRequest.getCurrentLatitude(), tripRequest.getCurrentLongitude(), allStation);

        List<Route> routes = routeRepo.findRoutesByStations(nearestStation, destinationStation);
        routes.get(0).getStations().forEach(routeStation -> {
            stationDtoList.add(stationMapper.mapToDto(routeStation.getStation()));
        });
        List<StationDto> finalStationList = new ArrayList<>();
        int startStationOrder = -1;
        int destinationStationOrder = -1;
        for (int i = 0; i < stationDtoList.size(); i++) {
            if (stationDtoList.get(i).getName().equals(nearestStation.getName())) {
                startStationOrder = i;
                break;
            }
        }

        for (int i = 0; i < stationDtoList.size(); i++) {
            if (stationDtoList.get(i).getName().equals(destinationStation.getName())) {
                destinationStationOrder = i;
                break;
            }
        }
        if (startStationOrder < destinationStationOrder) {
            for (int i = startStationOrder; i <= destinationStationOrder; i++) {
                finalStationList.add(stationDtoList.get(i));
            }
        } else {
            for (int i = startStationOrder; i >= destinationStationOrder; i--) {
                finalStationList.add(stationDtoList.get(i));
            }
        }
        tripResponse.setStationsList(finalStationList);
        tripResponse.setRouteId(routes.get(0).getId());
        return tripResponse;
    }

    @Override
    public StationDto updateStation(Long id, StationDto stationDto) {
        Station station = stationRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Station", "id", id));
        station.setLatitude(stationDto.getLatitude());
        station.setLongitude(stationDto.getLongitude());
        Station savedStation = stationRepo.save(station);
        return  stationMapper.mapToDto(savedStation);
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
