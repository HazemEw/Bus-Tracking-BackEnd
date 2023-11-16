package com.example.login.mapper;

import com.example.login.dtos.RouteDto;
import com.example.login.dtos.StationDto;
import com.example.login.model.Route;
import com.example.login.model.Station;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    RouteMapper MAPPER = Mappers.getMapper(RouteMapper.class);

    RouteDto mapToDto(Route route);

    Route mapToRoute(RouteDto routeDto);
}
