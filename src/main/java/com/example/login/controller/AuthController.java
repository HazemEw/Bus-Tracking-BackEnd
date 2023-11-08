package com.example.login.controller;

import com.example.login.dtos.AuthResponse;
import com.example.login.dtos.LoginRequest;
import com.example.login.dtos.AdminDto;
import com.example.login.service.AuthService;
import com.example.login.service.implementations.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
   @Autowired
   AuthService adminService;


   @PostMapping("/add")
    public AuthResponse registration(@RequestBody AdminDto adminDto){
       return adminService.addAdmin(adminDto);
   }

   @PostMapping("/login")

   public AuthResponse loginAdmin(@RequestBody LoginRequest loginRequest){

      return adminService.login(loginRequest);
   }

}
