package com.example.login.controller;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.RouteDto;
import com.example.login.dtos.StationDto;
import com.example.login.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @PostMapping()
    public RouteDto addRoute(@RequestBody RouteDto routeDto) {
        return routeService.addRoute(routeDto);
    }

    @GetMapping("/{id}")
    public RouteDto getRoute(@PathVariable Long id){
        return  routeService.getRoute(id);
    }

    @GetMapping()
    public List<RouteDto> getRoutes(){
        return  routeService.getRoutes();
    }

    @GetMapping("/getBuss/{id}")
    public List<BusDto> getBussInRoute(@PathVariable Long id){
        return routeService.getBussInRoute(id);
    }
}
