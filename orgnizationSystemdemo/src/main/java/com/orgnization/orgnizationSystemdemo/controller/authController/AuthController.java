package com.orgnization.orgnizationSystemdemo.controller.authController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.orgnization.orgnizationSystemdemo.busenissLogic.serviceImpl.AuthServiceImpl;
import com.orgnization.orgnizationSystemdemo.busenissLogic.specs.AuthService;
import com.orgnization.orgnizationSystemdemo.dto.auth.AuthRequest;
import com.orgnization.orgnizationSystemdemo.dto.auth.AuthResponse;
import com.orgnization.orgnizationSystemdemo.dto.auth.RegisterRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;  


  @PostMapping("/login")
  public ResponseEntity<AuthResponse>login(@RequestBody AuthRequest request){
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
      return ResponseEntity.ok(authService.register(request));
  }
}
