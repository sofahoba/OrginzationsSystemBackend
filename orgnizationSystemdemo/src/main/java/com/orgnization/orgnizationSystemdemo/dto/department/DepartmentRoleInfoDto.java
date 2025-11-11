package com.orgnization.orgnizationSystemdemo.dto.department;

import java.util.UUID;

import lombok.Data;

@Data
public class DepartmentRoleInfoDto {
  private UUID id;
  private String name;
  private String description;
}
