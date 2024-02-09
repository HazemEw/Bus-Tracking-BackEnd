package com.example.login.dtos;

import com.example.login.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private String phone;
    private String email;
    private String username;
    private String shift;
    private String secondShift;
    private DriverStatus driverStatus;
    private byte[] image;
}
