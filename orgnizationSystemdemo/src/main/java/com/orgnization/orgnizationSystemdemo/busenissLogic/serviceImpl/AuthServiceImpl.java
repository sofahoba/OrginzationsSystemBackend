package com.orgnization.orgnizationSystemdemo.busenissLogic.serviceImpl;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orgnization.orgnizationSystemdemo.busenissLogic.specs.AuthService;
import com.orgnization.orgnizationSystemdemo.config.security.CustomUserDetails;
import com.orgnization.orgnizationSystemdemo.dto.AuthRequest;
import com.orgnization.orgnizationSystemdemo.dto.AuthResponse;
import com.orgnization.orgnizationSystemdemo.dto.RegisterRequest;
import com.orgnization.orgnizationSystemdemo.model.entity.User;
import com.orgnization.orgnizationSystemdemo.repository.user.UserRepository;
import com.orgnization.orgnizationSystemdemo.util.JwtUtill;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtill jwtUtil;

  @Override
  public AuthResponse login(AuthRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
    
    var accessToken = jwtUtil.generateToken(new CustomUserDetails(user));
    var refreshToken = jwtUtil.generateRefreshToken(new CustomUserDetails(user));

    UUID departmentId = user.getDepartment() != null ? user.getDepartment().getId() : null;
    UUID departmentRoleId = user.getDepartmentRole() != null ? user.getDepartmentRole().getId() : null;

    return new AuthResponse(
        accessToken,
        refreshToken,
        departmentId,
        departmentRoleId
    );
  }

  @Override
  public String register(RegisterRequest request){

    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new RuntimeException("Email already exists");
    }

    User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .firstName(request.getUsername())
            .lastName("")
            .active(true)
            .type(null)
            .build();

    userRepository.save(user);
    return "User Created Successfully";
  }
}
