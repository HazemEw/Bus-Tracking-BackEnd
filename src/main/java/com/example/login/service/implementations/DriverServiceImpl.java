package com.example.login.service.implementations;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.DriverDto;
import com.example.login.dtos.RouteDto;
import com.example.login.enums.BusStatus;
import com.example.login.enums.DriverStatus;
import com.example.login.enums.Role;
import com.example.login.exceptions.CustomException;
import com.example.login.exceptions.DuplicateException;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.BusMapper;
import com.example.login.mapper.DriverMapper;
import com.example.login.mapper.RouteMapper;
import com.example.login.model.Bus;
import com.example.login.model.Driver;
import com.example.login.repo.BusRepo;
import com.example.login.repo.DriverRepo;
import com.example.login.service.DriverService;
import com.example.login.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;
    private final DriverMapper driverMapper;

    private final RouteService routeService;

    private final BusRepo busRepo;
    private final BusMapper busMapper;

    private final RouteMapper routeMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DriverDto addDriver(DriverDto driverDto) {
        if (driverRepo.findByUsername(driverDto.getUsername()).isEmpty()) {
            Driver driver = driverMapper.mapToDriver(driverDto);
            driver.setEmail(driverDto.getEmail());
            driver.setRole(Role.DRIVER);
            driver.setUsername(driverDto.getUsername());
            driver.setPassword(passwordEncoder.encode("tpb" +driverDto.getUsername()));
            driverRepo.save(driver);
            return driverMapper.mapToDto(driver);
        } else
            throw new DuplicateException(driverDto.getUsername());
    }

    @Override
    public DriverDto getDriver(Long driverId) {
        Driver driver = driverRepo.findById(driverId).orElseThrow(
                ()->new ResourceNotFoundException("Driver","id",driverId)
        );
        return driverMapper.mapToDto(driver);
    }

    @Override
    public List<DriverDto> getDrivers() {
       List<DriverDto> driverDtoList = new ArrayList<>();
       driverRepo.findAll().forEach(driver -> driverDtoList.add(
               driverMapper.mapToDto(driver)
       ));
       return driverDtoList;
    }

    @Override
    public List<DriverDto> getUnassignedDriversWithBus() {
        List<Driver> unassignedDrivers = driverRepo.findAllByBusIsNull();
        List<DriverDto> unassignedDriversDto= new ArrayList<>();
        unassignedDrivers.forEach(driver -> {
            unassignedDriversDto.add(driverMapper.mapToDto(driver));
        });
        return unassignedDriversDto;
    }

    @Override
    public void deleteDriver(Long driverId) {
      Driver driver = driverRepo.findById(driverId).orElseThrow(
              ()-> new ResourceNotFoundException("Driver","id",driverId)
      );
      driverRepo.delete(driver);
    }

    @Override
    public BusDto startShift(Long id) {
        Driver driver = driverRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Driver","id",id)
        );
        LocalTime currentTime = LocalTime.now();

        Bus bus = driver.getBus();
        bus.getDrivers().forEach(driver1 -> {
            if (driver1.getDriverStatus() == DriverStatus.IN_SHIFT)
                throw new CustomException("Another driver did not finish the shift on this bus. please until he finished the shift", HttpStatus.BAD_REQUEST);
        });
        driver.setDriverStatus(DriverStatus.IN_SHIFT);
        bus.setBusStatus(BusStatus.ACTIVE);
        Bus busSaved= busRepo.save(bus);
        driverRepo.save(driver);
        return busMapper.mapToDto(busSaved);
    }

    @Override
    public DriverDto endShift(Long id) {
        Driver driver = driverRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Driver","id",id)
        );
         if (driver.getDriverStatus() == DriverStatus.OUT_SHIFT)
             throw new CustomException("You are Not in Shift to End it",HttpStatus.BAD_REQUEST);
        driver.setDriverStatus(DriverStatus.OUT_SHIFT);
        Driver driverSaved = driverRepo.save(driver);
        Bus bus = driver.getBus();
        bus.setBusStatus(BusStatus.NOT_ACTIVE);
        Bus busSaved= busRepo.save(bus);
        driverRepo.save(driver);
        return driverMapper.mapToDto(driverSaved);
    }

    @Override
    public DriverDto changeImage(Long id, MultipartFile image) throws IOException {
        Driver driver = driverRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Driver","id",id)
        );
        driver.setImage(image.getBytes());
        Driver savedDriver = driverRepo.save(driver);
        return driverMapper.mapToDto(savedDriver);
    }

    @Override
    public DriverDto updateDriver(Long id, DriverDto driverDto) {
        Driver driver = driverRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Driver","id",id)
        );
        driver.setFirstName(driverDto.getFirstName());
        driver.setLastName(driverDto.getLastName());
        driver.setLicenseNumber(driverDto.getLicenseNumber());
        driver.setPhone(driverDto.getPhone());
        driver.setEmail(driverDto.getEmail());
        Driver savedDriver = driverRepo.save(driver);
        return driverMapper.mapToDto(savedDriver);
    }

    @Override
    public RouteDto getDriverRoute(Long id) {
        Driver driver = driverRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Driver","id",id)
        );
        return routeService.getRoute(driver.getBus().getRoute().getId());
    }
}
