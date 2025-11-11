package com.orgnization.orgnizationSystemdemo.busenissLogic.specs;

import com.orgnization.orgnizationSystemdemo.dto.auth.AuthRequest;
import com.orgnization.orgnizationSystemdemo.dto.auth.AuthResponse;
import com.orgnization.orgnizationSystemdemo.dto.auth.RegisterRequest;

public interface AuthService {
  AuthResponse login(AuthRequest request);
  String register(RegisterRequest request);
}
