package com.example.login.repo;

import com.example.login.enums.NotificationStatus;
import com.example.login.model.Notification;
import com.example.login.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
    List<Notification> findByNotificationType(String type);
    List<Notification> findByNotificationStatus(NotificationStatus status);
    List<Notification> findByNotificationStatusAndNotificationType(NotificationStatus notificationStatus, String notificationType);
    List<Notification> findByDriverId(Long driver_id);
}
