package com.example.login.controller;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.DriverDto;
import com.example.login.dtos.RouteDto;
import com.example.login.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/unassigned")
    public List<DriverDto> getUnassignedDriversWithBus(){
        return driverService.getUnassignedDriversWithBus();
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Long id){
        driverService.deleteDriver(id);
    }
    @PostMapping("/startShift/{id}")
     public BusDto startShift(@PathVariable Long id){
        return driverService.startShift(id);
    }

    @PostMapping("/endShift/{id}")
    public DriverDto endShift(@PathVariable Long id){
        return driverService.endShift(id);
    }

    @PostMapping("/changeImage/{id}")
    public DriverDto changeImage(@PathVariable Long id ,@RequestParam("image") MultipartFile image) throws IOException {
        return driverService.changeImage(id,image);
    }

    @PutMapping("/{id}")
    public DriverDto updateDriver(@PathVariable Long id ,@RequestBody DriverDto driverDto)  {
        return driverService.updateDriver(id,driverDto);
    }

    @GetMapping("/getRoute/{id}")
    public RouteDto getDriverRoute(@PathVariable Long id){
        return driverService.getDriverRoute(id);
    }

}
