package com.example.login.service.implementations;

import com.example.login.dtos.NotificationDto;
import com.example.login.enums.NotificationStatus;
import com.example.login.exceptions.ResourceNotFoundException;
import com.example.login.mapper.NotificationMapper;
import com.example.login.model.Driver;
import com.example.login.model.Notification;
import com.example.login.repo.DriverRepo;
import com.example.login.repo.NotificationRepo;
import com.example.login.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepo notificationRepo;

    private final DriverRepo driverRepo;

    @Override
    public NotificationDto addNotification(Long driverId,NotificationDto notificationDto) {
        Notification notification =  notificationMapper.mapToNotification(notificationDto);
        notification.setNotificationStatus(NotificationStatus.IN_PROCESS);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        notification.setCreationDate(currentDateTime.format(formatter));
        Driver driver = driverRepo.findById(driverId).get();
        driver.getNotifications().add(notification);
        notification.setDriver(driver);
        Notification savedNotification = notificationRepo.save(notification);
        return notificationMapper.mapToDto(savedNotification);
    }

    @Override
    public List<NotificationDto> getDriverNotification(Long driverId) {
        List<NotificationDto> notificationList =new ArrayList<>();
        List<Notification> notifications = notificationRepo.findByDriverId(driverId);
        notifications.stream().forEach(notification -> {
            notificationList.add(notificationMapper.mapToDto(notification));
        });
        return notificationList;
    }

    @Override
    public List<NotificationDto> getNotifications() {
       List<NotificationDto> notificationList =new ArrayList<>();
       List<Notification> notifications = notificationRepo.findAll();
       notifications.stream().forEach(notification -> {
           notificationList.add(notificationMapper.mapToDto(notification));
       });

       return notificationList;
    }

    @Override
    public List<NotificationDto> getExceptions() {
        List<NotificationDto> notificationList =new ArrayList<>();
        List<Notification> notifications = notificationRepo.findByNotificationType("Exception");
        notifications.stream().forEach(notification -> {
            notificationList.add(notificationMapper.mapToDto(notification));
        });

        return notificationList;
    }

    @Override
    public List<NotificationDto> getAbsents() {
        List<NotificationDto> notificationList =new ArrayList<>();
        List<Notification> notifications = notificationRepo.findByNotificationType("Absent");
        notifications.stream().forEach(notification -> {
            notificationList.add(notificationMapper.mapToDto(notification));
        });

        return notificationList;
    }

    @Override
    public List<NotificationDto> getExceptionsInProcess() {
        List<NotificationDto> notificationList =new ArrayList<>();
        List<Notification> notifications = notificationRepo.findByNotificationStatusAndNotificationType(NotificationStatus.IN_PROCESS,"Exception");
        notifications.stream().forEach(notification -> {
            notificationList.add(notificationMapper.mapToDto(notification));
        });

        return notificationList;
    }

    @Override
    public List<NotificationDto> getAbsentInProcess() {
        List<NotificationDto> notificationList =new ArrayList<>();
        List<Notification> notifications = notificationRepo.findByNotificationStatusAndNotificationType(NotificationStatus.IN_PROCESS,"Absent");
        notifications.stream().forEach(notification -> {
            notificationList.add(notificationMapper.mapToDto(notification));
        });

        return notificationList;
    }

    @Override
    public NotificationDto acceptNotification(Long id , String massage) {
        Notification notification = notificationRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Notification","id",id)
        );
        notification.setNotificationStatus(NotificationStatus.ACCEPTED);
        notification.setMassage(massage);
        Notification savedNotification = notificationRepo.save(notification);
        return notificationMapper.mapToDto(savedNotification);

    }

    @Override
    public NotificationDto rejectNotification(Long id ,String massage) {
        Notification notification = notificationRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Notification","id",id)
        );
        notification.setMassage(massage);
        notification.setNotificationStatus(NotificationStatus.REJECTED);
        Notification savedNotification = notificationRepo.save(notification);
        return notificationMapper.mapToDto(savedNotification);
    }


}
