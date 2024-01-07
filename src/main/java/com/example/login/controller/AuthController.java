package com.example.login.controller;

import com.example.login.dtos.AuthResponse;
import com.example.login.dtos.LoginRequest;
import com.example.login.dtos.AdminDto;
import com.example.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
   @Autowired
   AuthService authService;


   @PostMapping("/addAdmin")
    public AuthResponse registration(@RequestBody AdminDto adminDto){
      return authService.addAdmin(adminDto);
   }


   @PostMapping("/adminLogin")
   public AuthResponse adminLogin(@RequestBody LoginRequest loginRequest){
      return authService.adminLogin(loginRequest);
   }

   @PostMapping("/driverLogin")
   public AuthResponse driverLogin(@RequestBody LoginRequest loginRequest){
      return authService.driverLogin(loginRequest);
   }

}
