package com.example.login.model;

import com.example.login.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creationDate;

    private String applicationDate;

    private String day;

    private String note;

    private String notificationType;

    private NotificationStatus notificationStatus;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

}
