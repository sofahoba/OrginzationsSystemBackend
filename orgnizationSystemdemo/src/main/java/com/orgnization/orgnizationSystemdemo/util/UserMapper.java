package com.orgnization.orgnizationSystemdemo.util;

import org.springframework.stereotype.Component;

import com.orgnization.orgnizationSystemdemo.dto.user.SimpleUserDto;
import com.orgnization.orgnizationSystemdemo.model.entity.User;

@Component
public class UserMapper {
  
  public static SimpleUserDto toDto(User user) {
    if (user == null) return null;

    SimpleUserDto dto = new SimpleUserDto();
    dto.setId(user.getId());
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setEmail(user.getEmail());
    dto.setType(user.getType());
    dto.setDepartmentId(user.getDepartment() != null ? user.getDepartment().getId() : null);
    dto.setDepartmentRoleId(user.getDepartmentRole() != null ? user.getDepartmentRole().getId() : null);

    return dto;
  } 
}
