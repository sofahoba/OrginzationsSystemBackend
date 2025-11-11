package com.orgnization.orgnizationSystemdemo.dto.auth;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
  private String accessToken;
  private String refreshToken;
  private UUID departmentId;
  private UUID departmentRoleId;
}
