package com.example.login.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "route_station")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteStation {
    @EmbeddedId
    private RouteStationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("routeId")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stationId")
    private Station station;

    @Column(name = "stationOrder")
    private Long stationOrder;

    public RouteStation(Route route, Station station, Long stationOrder) {
        this.route = route;
        this.station = station;
        this.stationOrder = stationOrder;
        this.id = new RouteStationId(route.getId(),station.getId());
    }
}
