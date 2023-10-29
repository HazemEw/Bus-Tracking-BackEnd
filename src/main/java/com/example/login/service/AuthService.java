package com.example.login.service;

import com.example.login.dtos.AdminDto;
import com.example.login.dtos.AuthResponse;
import com.example.login.dtos.LoginRequest;

public interface AuthService {
    AuthResponse addAdmin(AdminDto adminDto);
    AuthResponse login(LoginRequest loginRequest);
}
