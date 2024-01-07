package com.example.login.service.implementations;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.DriverDto;
import com.example.login.enums.BusStatus;
import com.example.login.enums.DriverStatus;
import com.example.login.enums.Role;
import com.example.login.exceptions.CustomException;
import com.example.login.exceptions.DuplicateException;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.BusMapper;
import com.example.login.mapper.DriverMapper;
import com.example.login.model.Bus;
import com.example.login.model.Driver;
import com.example.login.repo.BusRepo;
import com.example.login.repo.DriverRepo;
import com.example.login.service.DriverService;
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

    private final BusRepo busRepo;
    private final BusMapper busMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DriverDto addDriver(DriverDto driverDto) {
        if (!driverRepo.findByUsername(driverDto.getUsername()).isPresent()) {
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
        DriverDto driverDto = driverMapper.mapToDto(driver);
        return driverDto;
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
        unassignedDrivers.stream().forEach(driver -> {
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
        if (driver.getSecondShift()!= null){
            if ((currentTime.isBefore(LocalTime.parse("05:55")) || currentTime.isAfter(LocalTime.parse("07:00")))) {
                throw new CustomException("shift cannot start before 5:55 AM Or after 7:00 AM", HttpStatus.BAD_REQUEST);
            }

        }
        if (driver.getShift().equalsIgnoreCase("Morning") && (currentTime.isBefore(LocalTime.parse("05:55")) || currentTime.isAfter(LocalTime.parse("07:00")))) {
            throw new CustomException("Morning shift cannot start before 5:55 AM Or after 7:00 AM", HttpStatus.BAD_REQUEST);
        }

        if (driver.getShift().equalsIgnoreCase("Evening") && (currentTime.isBefore(LocalTime.parse("14:55")) || currentTime.isAfter(LocalTime.parse("16:00")))) {
            throw new CustomException("Evening shift cannot start before 2:55 PM Or after 4:00 PM", HttpStatus.BAD_REQUEST);
        }

        Bus bus = driver.getBus();
        bus.getDrivers().stream().forEach(driver1 -> {
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
}
