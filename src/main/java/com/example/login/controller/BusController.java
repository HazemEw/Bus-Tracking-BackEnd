package com.example.login.controller;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.DriverDto;
import com.example.login.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @GetMapping()
    public List<BusDto> readBuss(){
        return busService.readBuss();
    }

    @GetMapping("/{id}")
    public BusDto readBus(@PathVariable Long id){
        return busService.readBus(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    public BusDto updateBus(@RequestBody  BusDto busDto, @PathVariable Long id){
        return busService.updateBus(busDto,id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("changeLocation/{id}")
    public BusDto updateBusLocation(@RequestBody  BusDto busDto, @PathVariable Long id){
        return busService.updatedBusLocation(busDto,id);
    }

    @GetMapping("/byPermitNumber")
    public BusDto getByPermitNumber(@RequestParam String permitNumber){
        return busService.getByPermitNumber(permitNumber);
    }


}
