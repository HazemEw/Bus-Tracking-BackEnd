package com.example.login.service.implementations;

import com.example.login.dtos.BusDto;
import com.example.login.exceptions.DuplicateException;
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
            Driver driver = driverRepo.findByUsername(busDto.getDriver().getUsername()).get();
            Route route = routeRepo.findByName(busDto.getRoute().getName()).get();
            bus.setDriver(driver);
            bus.setRoute(route);
            Bus savedBus = busRepo.save(bus);
            BusDto savedBusDto = busMapper.mapToDto(savedBus);
            savedBusDto.setRoute(routeMapper.mapToDto(savedBus.getRoute()));
            savedBusDto.setDriver(driverMapper.mapToDto(savedBus.getDriver()));
            return savedBusDto;
        }else
            throw new DuplicateException(busDto.getPermitNumber());

    }

    @Override
    public BusDto readBus(Long id) {
        return null;
    }

    @Override
    public List<BusDto> readBuss() {
        return null;
    }
}
