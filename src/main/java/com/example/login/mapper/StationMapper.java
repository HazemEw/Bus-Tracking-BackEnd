package com.example.login.mapper;

import com.example.login.dtos.AdminDto;
import com.example.login.dtos.StationDto;
import com.example.login.model.Admin;
import com.example.login.model.Station;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StationMapper {

    StationMapper MAPPER = Mappers.getMapper(StationMapper.class);

    StationDto mapToDto(Station station);

    Station mapToStation(StationDto stationDto);
}
