package com.orgnization.orgnizationSystemdemo.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import com.orgnization.orgnizationSystemdemo.dto.department.DepartmentResponseDto;
import com.orgnization.orgnizationSystemdemo.dto.department.DepartmentRoleInfoDto;
import com.orgnization.orgnizationSystemdemo.model.entity.Department;

@Component
public class DepartmentMapper {
  
  public static DepartmentResponseDto toDto(Department department) {
    if (department == null) return null;
    DepartmentResponseDto dto = new DepartmentResponseDto();
    dto.setId(department.getId());
    dto.setName(department.getName());
    dto.setDescription(department.getDescription());
    Set<DepartmentRoleInfoDto> roleDtos = new HashSet<>();
    if (Hibernate.isInitialized(department.getDepartmentRoles())) {
        roleDtos = department.getDepartmentRoles().stream()
            .map(role -> {
                DepartmentRoleInfoDto rDto = new DepartmentRoleInfoDto();
                rDto.setId(role.getId());
                rDto.setName(role.getName());
                rDto.setDescription(role.getDescription());
                return rDto;
            }).collect(Collectors.toSet());
    }
    dto.setDepartmentRoles(roleDtos);
    dto.setCreatedAt(department.getCreatedAt());
    return dto;
}
}
