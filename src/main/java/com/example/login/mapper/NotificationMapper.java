package com.example.login.mapper;

import com.example.login.dtos.NotificationDto;
import com.example.login.dtos.RouteDto;
import com.example.login.model.Notification;
import com.example.login.model.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper MAPPER = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "driverId", source = "driver.id")
    NotificationDto mapToDto(Notification notification);

    Notification mapToNotification(NotificationDto notificationDto);
}
