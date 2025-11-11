package com.orgnization.orgnizationSystemdemo.dto.user;

import java.util.UUID;

import com.orgnization.orgnizationSystemdemo.model.enums.SeniorityType;

import lombok.Data;

@Data
public class CreatUserByAdmin {
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private SeniorityType type;
  private UUID departmentId;
  private UUID departmentRoleId;
}
