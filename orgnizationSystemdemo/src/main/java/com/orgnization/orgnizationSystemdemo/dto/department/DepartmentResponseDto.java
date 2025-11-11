package com.orgnization.orgnizationSystemdemo.dto.department;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


import lombok.Data;

@Data
public class DepartmentResponseDto {
  private UUID id;
  private String name;
  private String description;
  private Set<DepartmentRoleInfoDto> departmentRoles;
  private LocalDateTime createdAt;
}
