package com.example.login.controller;

import com.example.login.dtos.DriverDto;
import com.example.login.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public DriverDto addDriver(@RequestBody  DriverDto driverDto){
        return driverService.addDriver(driverDto);
    }

    @GetMapping("/{id}")
    public DriverDto getDriver(@PathVariable Long id){
        return driverService.getDriver(id);
    }

    @GetMapping()
    public List<DriverDto> getDrivers(){
        return driverService.getDrivers();
    }
    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Long id){
        driverService.deleteDriver(id);
    }
}
