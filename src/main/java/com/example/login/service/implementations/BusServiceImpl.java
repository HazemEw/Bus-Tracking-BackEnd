package com.example.login.service.implementations;

import com.example.login.dtos.BusDto;
import com.example.login.exceptions.DuplicateException;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.BusMapper;
import com.example.login.mapper.DriverMapper;
import com.example.login.mapper.RouteMapper;
import com.example.login.model.Bus;
import com.example.login.model.Driver;
import com.example.login.model.Route;
import com.example.login.repo.BusRepo;
import com.example.login.repo.DriverRepo;
import com.example.login.repo.RouteRepo;
import com.example.login.service.BusService;
import com.example.login.service.DriverService;
import com.example.login.service.RouteService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BusServiceImpl implements BusService {

    private final BusMapper busMapper;
    private final BusRepo busRepo;

    private final DriverRepo driverRepo;
    private final RouteRepo routeRepo;

    private final DriverMapper driverMapper;
    private final RouteMapper routeMapper;
    @Override
    public BusDto addBus(BusDto busDto) {
        if (!busRepo.findByPermitNumber(busDto.getPermitNumber()).isPresent()){
            Bus bus = busMapper.mapToBus(busDto);
            List<Driver> drivers = new ArrayList<>();
            busDto.getDrivers().stream().forEach(driverDto -> {
                Driver driver = driverRepo.findByUsername(driverDto.getUsername()).get();
                driver.setBus(bus);
                drivers.add(driver);
            });
            Route route = routeRepo.findByName(busDto.getRoute().getName()).get();
            System.out.println(drivers);
            bus.setDrivers(drivers);
            bus.setRoute(route);
            Bus savedBus = busRepo.save(bus);
            BusDto savedBusDto = busMapper.mapToDto(savedBus);
            return savedBusDto;
        }else
            throw new DuplicateException(busDto.getPermitNumber());


    }

    @Override
    public BusDto readBus(Long id) {
        Bus bus = busRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Bus","id",id)
        );
        return busMapper.mapToDto(bus);
    }

    @Override
    public List<BusDto> readBuss() {
        List<BusDto> buss = new ArrayList<>();
        busRepo.findAll().forEach(bus -> {
            buss.add(busMapper.mapToDto(bus));
        });
        return buss;
    }

    @Override
    public BusDto updateBus(BusDto busDto, Long id) {
        Bus bus = busRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Bus","id",id)
        );
        bus.setBusStatus(busDto.getBusStatus());
        bus.setLatitude(busDto.getLatitude());
        bus.setLongitude(busDto.getLongitude());
        Bus savedBus = busRepo.save(bus);
        return busMapper.mapToDto(bus);
    }

    @Override
    public BusDto updatedBusLocation(BusDto busDto, Long id) {
        Bus bus = busRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Bus","id",id)
        );
        bus.setLatitude(busDto.getLatitude());
        bus.setLongitude(busDto.getLongitude());
        Bus savedBus = busRepo.save(bus);
        return busMapper.mapToDto(bus);
    }

    @Override
    public BusDto getByPermitNumber(String permitNumber) {
        Bus bus = busRepo.findByPermitNumber(permitNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Bus","Permit Number" ,permitNumber)
        );

        return busMapper.mapToDto(bus);
    }
}
