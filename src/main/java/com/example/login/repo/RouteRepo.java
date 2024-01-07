package com.example.login.repo;

import com.example.login.model.Route;
import com.example.login.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouteRepo extends JpaRepository<Route, Long> {
    Optional<Route> findByName(String name);

    @Query("SELECT r FROM Route r " +
            "JOIN r.stations rs1 " +
            "JOIN r.stations rs2 " +
            "WHERE rs1.station = :station1 AND rs2.station = :station2")
    List<Route> findRoutesByStations(@Param("station1") Station station1, @Param("station2") Station station2);


}
