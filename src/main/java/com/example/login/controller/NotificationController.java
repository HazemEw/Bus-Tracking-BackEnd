package com.example.login.controller;

import com.example.login.dtos.DriverDto;
import com.example.login.dtos.NotificationDto;
import com.example.login.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add/{id}")
    public NotificationDto addNotification(@PathVariable Long id ,@RequestBody NotificationDto notificationDto){
        return notificationService.addNotification(id, notificationDto);
    }

    @GetMapping("/driver/{driverId}")
    public List<NotificationDto> getDriverNotification(@PathVariable Long driverId) {
        return notificationService.getDriverNotification(driverId);
    }

    @GetMapping()
    public List<NotificationDto> getNotifications() {
        return notificationService.getNotifications();
    }

    @GetMapping("/exceptions")
    public List<NotificationDto> getExceptions() {
        return notificationService.getExceptions();
    }

    @GetMapping("/absents")
    public List<NotificationDto> getAbsents() {
        return notificationService.getAbsents();
    }

    @GetMapping("/exceptions/process")
    public List<NotificationDto> getExceptionsInProcess() {
        return notificationService.getExceptionsInProcess();
    }

    @GetMapping("/absents/process")
    public List<NotificationDto> getAbsentInProcess() {
        return notificationService.getAbsentInProcess();
    }

    @PutMapping("/accept/{id}")
    public NotificationDto acceptNotification(@PathVariable Long id) {
        return notificationService.acceptNotification(id);
    }

    @PutMapping("/reject/{id}")
    public NotificationDto rejectNotification(@PathVariable Long id) {
        return notificationService.rejectNotification(id);
    }
}
