package com.example.login.mapper;

import com.example.login.dtos.DriverDto;
import com.example.login.dtos.StationDto;
import com.example.login.model.Driver;
import com.example.login.model.Station;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverMapper MAPPER = Mappers.getMapper(DriverMapper.class);

    DriverDto mapToDto(Driver driver);

    Driver mapToDriver(DriverDto driverDto);
}
