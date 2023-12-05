package com.example.login.repo;

import com.example.login.model.Bus;
import com.example.login.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepo extends JpaRepository<Bus,Long> {
    Optional<Bus> findByPermitNumber(String permitNumber);
}
