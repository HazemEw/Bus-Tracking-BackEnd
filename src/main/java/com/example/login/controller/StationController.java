package com.example.login.controller;

import com.example.login.dtos.StationDto;
import com.example.login.service.StationService;
import com.example.login.service.implementations.StationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("api/stations")
@AllArgsConstructor
public class StationController {

    private final StationService stationService;

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public StationDto addStation(@RequestBody StationDto stationDto){
        return stationService.addStation(stationDto);
    }

    @GetMapping()
    public List<StationDto> getStations(){
        return stationService.getStations();
    }
    @GetMapping("/{id}")
    public StationDto getStation( @PathVariable Long id){
        return stationService.getStation(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable Long id){
        stationService.deleteStation(id);
    }
}
