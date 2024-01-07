package com.example.login.model;

import com.example.login.enums.BusStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String permitNumber;

    private BusStatus busStatus;

    private double latitude;

    private double longitude;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Driver> drivers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public  boolean  isFullShift() {
     if (this.drivers.size()==2)
         return true;
     else
         return false;
    }
    public boolean hasMorningShift() {
        return drivers
                .stream()
                .anyMatch(driver -> "Morning".equals(driver.getShift()));
    }

    public boolean hasEveningShift() {
        return drivers
                .stream()
                .anyMatch(driver -> "Evening".equals(driver.getShift()));
    }

}
