package com.example.login.service;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.DriverDto;
import com.example.login.dtos.RouteDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DriverService {

    DriverDto addDriver(DriverDto driverDto);
    DriverDto getDriver(Long DriverId);
    List<DriverDto> getDrivers();
    List<DriverDto> getUnassignedDriversWithBus();
    void deleteDriver(Long driverId);
    BusDto startShift(Long id);
    DriverDto endShift(Long id);

    DriverDto changeImage(Long id, MultipartFile image) throws IOException;

    DriverDto updateDriver(Long id, DriverDto driverDto);

    RouteDto getDriverRoute(Long id);
}
