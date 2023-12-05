package com.example.login.mapper;

import com.example.login.dtos.BusDto;
import com.example.login.dtos.RouteDto;
import com.example.login.model.Bus;
import com.example.login.model.Route;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface  BusMapper {
    BusMapper MAPPER = Mappers.getMapper(BusMapper.class);

    BusDto mapToDto(Bus bus);
    Bus mapToBus(BusDto busDto);
}
