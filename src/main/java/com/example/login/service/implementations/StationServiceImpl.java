package com.example.login.service.implementations;

import com.example.login.dtos.StationDto;
import com.example.login.exceptions.DuplicateException;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.StationMapper;
import com.example.login.model.Station;
import com.example.login.repo.StationRepo;
import com.example.login.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepo stationRepo;

    private final StationMapper stationMapper;

    public StationDto addStation(StationDto stationDto) {
        if (!stationRepo.findByName(stationDto.getName()).isPresent()) {
            Station station = stationMapper.mapToStation(stationDto);
            stationRepo.save(station);
            return stationDto;
        } else
            throw new DuplicateException(stationDto.getName());
    }

    public List<StationDto> getStations() {
        List<StationDto> stationDtoList= new ArrayList<>();
        stationRepo.findAll().forEach(station -> stationDtoList.add(
                stationMapper.mapToDto(station)
        ));
        return stationDtoList;
    }

    public StationDto getStation(Long id){
        Station station = stationRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Station","id",id)
        );
        StationDto stationDto = stationMapper.mapToDto(station);
        return stationDto;
    }

    public void deleteStation(Long id) {
        Station station = stationRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Station","id",id)
        );
        stationRepo.delete(station);
    }
}
