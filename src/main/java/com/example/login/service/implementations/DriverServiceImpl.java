package com.example.login.service.implementations;

import com.example.login.dtos.DriverDto;
import com.example.login.exceptions.DuplicateException;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.DriverMapper;
import com.example.login.model.Driver;
import com.example.login.repo.DriverRepo;
import com.example.login.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;
    private final DriverMapper driverMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public DriverDto addDriver(DriverDto driverDto) {
        if (!driverRepo.findByUsername(driverDto.getUsername()).isPresent()) {
            Driver driver = driverMapper.mapToDriver(driverDto);
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
    public void deleteDriver(Long driverId) {
      Driver driver = driverRepo.findById(driverId).orElseThrow(
              ()-> new ResourceNotFoundException("Driver","id",driverId)
      );
      driverRepo.delete(driver);
    }
}
