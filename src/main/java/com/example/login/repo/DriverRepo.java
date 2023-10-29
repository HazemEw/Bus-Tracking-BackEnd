package com.example.login.repo;

import com.example.login.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepo extends JpaRepository<Driver,Long> {
    Optional<Driver> findByUsername(String username);
}
