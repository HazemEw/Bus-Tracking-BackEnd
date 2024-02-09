package com.example.login.service;

import com.example.login.dtos.NotificationDto;
import com.example.login.dtos.RouteDto;
import com.example.login.model.Notification;

import java.util.List;

public interface NotificationService {
    NotificationDto addNotification(Long driverId,NotificationDto notificationDto);

    List<NotificationDto> getDriverNotification(Long driverId);

    List<NotificationDto> getNotifications();

    List<NotificationDto> getExceptions();

    List<NotificationDto> getAbsents();

    List<NotificationDto> getExceptionsInProcess();

    List<NotificationDto> getAbsentInProcess();

    NotificationDto acceptNotification(Long id ,String massage);

    NotificationDto rejectNotification(Long id, String massage);



}
