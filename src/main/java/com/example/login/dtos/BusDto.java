package com.example.login.dtos;

import com.example.login.enums.BusStatus;
import com.example.login.model.Driver;
import com.example.login.model.Route;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  BusDto {

    private Long id;

    private String permitNumber;

    private RouteDto route;

    private List<DriverDto> drivers = new ArrayList<>();

    private BusStatus busStatus;

    private double latitude;

    private double longitude;

}
