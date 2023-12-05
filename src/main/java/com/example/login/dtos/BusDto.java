package com.example.login.dtos;

import com.example.login.model.Driver;
import com.example.login.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class    BusDto {

    private Long id;
    private String permitNumber;
    private DriverDto driver;
    private RouteDto route;

}
