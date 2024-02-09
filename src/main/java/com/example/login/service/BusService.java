package com.example.login.service;

import com.example.login.dtos.BusDto;
import com.example.login.model.Bus;

import java.util.List;

public interface BusService {
    BusDto addBus(BusDto busDto);
    BusDto readBus(Long id);
    List<BusDto> readBuss();
    BusDto updateBus(BusDto busDto ,Long id);

    BusDto updatedBusLocation(BusDto busDto,Long id);

    BusDto getByPermitNumber(String permitNumber);
}
