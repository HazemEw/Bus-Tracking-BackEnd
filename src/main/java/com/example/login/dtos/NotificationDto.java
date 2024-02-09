package com.example.login.dtos;

import com.example.login.enums.NotificationStatus;
import com.example.login.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private Long id;
    private String creationDate;
    private String applicationDate;
    private String day;
    private String note;
    private String notificationType;
    private NotificationStatus notificationStatus;
    private Long driverId;
    private String massage;
}
