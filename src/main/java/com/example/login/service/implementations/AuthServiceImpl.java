package com.example.login.service.implementations;

import com.example.login.config.JwtService;
import com.example.login.dtos.AdminDto;
import com.example.login.dtos.AuthResponse;
import com.example.login.dtos.LoginRequest;
import com.example.login.enums.Role;
import com.example.login.exceptions.CustomException;
import com.example.login.exceptions.DuplicateException;
import com.example.login.mapper.AdminMapper;
import com.example.login.model.*;
import com.example.login.repo.AdminRepo;
import com.example.login.repo.UserRepo;
import com.example.login.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    private final AdminMapper adminMapper;

    private final AuthenticationManager authenticationManager;

    public AuthResponse addAdmin(AdminDto adminDto) {

        if (!adminRepo.findByUsername(adminDto.getUsername()).isPresent()) {
            AuthResponse authResponse = new AuthResponse();
            Admin admin = adminMapper.mapToAdmin(adminDto);
            admin.setEmail(adminDto.getEmail());
            admin.setUsername(adminDto.getUsername());
            admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
            admin.setRole(Role.ADMIN);
            adminRepo.save(admin);
            authResponse.setJwtToken(jwtService.generateToken(admin));
            return authResponse;
        } else {
            throw new DuplicateException(adminDto.getUsername());
        }


    }


    public AuthResponse adminLogin(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            AuthResponse authResponse = new AuthResponse();
            User user = userRepo.findByUsername(request.getUsername()).get();
            if (!user.getRole().equals(Role.ADMIN))
                throw new CustomException("Incorrect Username or Password",HttpStatus.BAD_REQUEST);
            authResponse.setJwtToken(jwtService.generateToken(user));
            return authResponse;
        } catch (BadCredentialsException e) {
            throw new CustomException("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        } catch (UsernameNotFoundException e) {
            throw new CustomException("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public AuthResponse driverLogin(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            AuthResponse authResponse = new AuthResponse();
            User user = userRepo.findByUsername(request.getUsername()).get();
            if (!user.getRole().equals(Role.DRIVER))
                throw new CustomException("Incorrect Username or Password",HttpStatus.BAD_REQUEST);
            authResponse.setJwtToken(jwtService.generateToken(user));
            return authResponse;
        } catch (BadCredentialsException e) {
            throw new CustomException("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        } catch (UsernameNotFoundException e) {
            throw new CustomException("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
     }

}
