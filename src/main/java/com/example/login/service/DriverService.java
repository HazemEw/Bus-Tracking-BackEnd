package com.example.login.service;

import com.example.login.dtos.DriverDto;

import java.util.List;

public interface DriverService {

    DriverDto addDriver(DriverDto driverDto);
    DriverDto getDriver(Long DriverId);
    List<DriverDto> getDrivers();

    List<DriverDto> getUnassignedDriversWithBus();
    void deleteDriver(Long driverId);
}
