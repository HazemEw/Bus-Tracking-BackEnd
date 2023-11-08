package com.example.login.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
