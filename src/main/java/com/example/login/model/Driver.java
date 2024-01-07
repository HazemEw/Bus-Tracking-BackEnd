package com.example.login.model;

import com.example.login.enums.DriverStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "driver")
public class Driver extends User {

    private String firstName;
    private String lastName;
    private String phone;
    private String licenseNumber;
    private String shift;
    private String secondShift;
    private DriverStatus driverStatus;



    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;



}
