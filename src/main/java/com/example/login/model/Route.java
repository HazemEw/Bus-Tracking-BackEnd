package com.example.login.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "route",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RouteStation> stations = new ArrayList<>();

    public void addStation(Station station , Long order) {
        RouteStation routeStation = new RouteStation(this,station,order);
        stations.add(routeStation);
        station.getRoutes().add(routeStation);
    }
}
