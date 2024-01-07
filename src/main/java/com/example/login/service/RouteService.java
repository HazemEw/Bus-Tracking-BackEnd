package com.example.login.service;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.RouteDto;

import java.util.List;

public interface RouteService {
      RouteDto addRoute(RouteDto routeDto);

    RouteDto getRoute(Long id);

    List<RouteDto> getRoutes();

    List<BusDto> getBussInRoute(Long id);
}
