package com.example.login.mapper;

import com.example.login.dtos.AdminDto;
import com.example.login.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AdminMapper {

  AdminMapper MAPPER = Mappers.getMapper(AdminMapper.class);

  AdminDto mapToDto(Admin admin);

  Admin mapToAdmin(AdminDto adminDto);

}
