package com.example.login.service.implementations;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.RouteDto;
import com.example.login.dtos.StationDto;
import com.example.login.exceptions.DuplicateException;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.BusMapper;
import com.example.login.mapper.RouteMapper;
import com.example.login.mapper.StationMapper;
import com.example.login.model.Route;
import com.example.login.model.RouteStation;
import com.example.login.model.Station;
import com.example.login.repo.RouteRepo;
import com.example.login.repo.StationRepo;
import com.example.login.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepo routeRepo;

    private final StationRepo stationRepo;

    private final StationMapper stationMapper;
    private final RouteMapper routeMapper;

    private final BusMapper busMapper;

    @Override
    public RouteDto addRoute(RouteDto routeDto) {
        if (routeRepo.findByName(routeDto.getName()).isEmpty()) {
            Route route = routeMapper.mapToRoute(routeDto);
            List<StationDto> stationDtoList = routeDto.getStationList();
            IntStream.range(0, stationDtoList.size())
                    .forEach(index -> {
                        String stationName = stationDtoList.get(index).getName();
                        Station station = stationRepo.findByName(stationName).orElseThrow(
                                () -> new ResourceNotFoundException("Station", "Name", stationName)
                        );
                        route.addStation(station, (long) index);
                    });
            routeRepo.save(route);
            List<RouteStation> stations = new ArrayList<>();
            stations = route.getStations();
            List<StationDto> stationList = new ArrayList<>();
            stations.forEach(routeStation -> {
                stationList.add(stationMapper.mapToDto(routeStation.getStation()));
            });

            RouteDto routeDto1 = routeMapper.mapToDto(routeRepo.findById(route.getId()).get());
            routeDto1.setStationList(stationList);
            return routeDto1;
        } else throw new DuplicateException(routeDto.getName());
    }

    @Override
    public RouteDto getRoute(Long id) {
        Route route = routeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Route", "ID", id)
        );
        RouteDto routeDto = routeMapper.mapToDto(route);
        List<StationDto> stationList = new ArrayList<>();
        route.getStations().forEach(
                routeStation -> {
                    stationList.add(stationMapper.mapToDto(routeStation.getStation()));
                }
        );
        routeDto.setStationList(stationList);
        return routeDto;
    }

    @Override
    public List<RouteDto> getRoutes() {
        List<RouteDto> routeDtoList = new ArrayList<>();
        routeRepo.findAll().forEach(route -> {
            routeDtoList.add(getRoute(route.getId()));
        });
        return routeDtoList;
    }

    @Override
    public List<BusDto> getBussInRoute(Long id) {
        List<BusDto> busList = new ArrayList<>();
        Route route = routeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Route", "id", id)
        );
        route.getBuses().forEach(bus -> {
            busList.add(busMapper.mapToDto(bus));
        });

        return busList;
    }

    @Override
    public List<RouteDto> getRoutesByCity(String cityName) {
        List<Route> routeList = routeRepo.findAll();
        List<RouteDto> routeDtoList = new ArrayList<>();
        for (Route route : routeList) {
            boolean hasMatchingCity = route.getStations().stream()
                    .anyMatch(routeStation -> {
                        return routeStation.getStation().getCity() != null &&
                                routeStation.getStation().getCity().equalsIgnoreCase(cityName);
                    });
            if (hasMatchingCity) {
                routeDtoList.add(routeMapper.mapToDto(route));
            }
        }

        return routeDtoList;
    }

}
