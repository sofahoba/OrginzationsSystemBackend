package com.orgnization.orgnizationSystemdemo.busenissLogic.specs;

import com.orgnization.orgnizationSystemdemo.dto.AuthRequest;
import com.orgnization.orgnizationSystemdemo.dto.AuthResponse;
import com.orgnization.orgnizationSystemdemo.dto.RegisterRequest;

public interface AuthService {
  AuthResponse login(AuthRequest request);
  String register(RegisterRequest request);
}
