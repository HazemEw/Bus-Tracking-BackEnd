package com.example.login.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)

    private  Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String licenseNumber;
    private String username;
    private String password;

    @OneToOne(mappedBy = "driver")
    private Bus bus;
}
