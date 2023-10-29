package com.example.login.repo;

import com.example.login.model.Admin;
import com.example.login.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationRepo extends JpaRepository<Station, Long> {
    Optional<Station> findByName(String name);
}
