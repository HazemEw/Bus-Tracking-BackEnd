package com.example.login.service;

import com.example.login.dtos.BusDto;

import java.util.List;

public interface BusService {
    BusDto addBus(BusDto busDto);
    BusDto readBus(Long id);
    List<BusDto> readBuss();

}
