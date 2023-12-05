package com.example.login.controller;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.DriverDto;
import com.example.login.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/buss")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public BusDto addBus(@RequestBody  BusDto busDto){
        return busService.addBus(busDto);
    }

}
