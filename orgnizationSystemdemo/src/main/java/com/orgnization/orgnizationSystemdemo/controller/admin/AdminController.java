package com.orgnization.orgnizationSystemdemo.controller.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orgnization.orgnizationSystemdemo.busenissLogic.specs.AdminService;
import com.orgnization.orgnizationSystemdemo.dto.user.CreatUserByAdmin;
import com.orgnization.orgnizationSystemdemo.dto.user.SimpleUserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/users/{userId}")
  public ResponseEntity<SimpleUserDto> getUserById(@PathVariable UUID userId) {
    SimpleUserDto userDto = adminService.getUserById(userId);
    return ResponseEntity.ok(userDto);
  } 

  @PostMapping("/users")
  public ResponseEntity<SimpleUserDto> createUser(@RequestBody CreatUserByAdmin request) {
    SimpleUserDto userDto = adminService.createUser(request);
    return ResponseEntity.ok(userDto);
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<String> deleteUserById(@PathVariable UUID userId) {
    String response = adminService.deleteUserById(userId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("users/{userId}")
  public ResponseEntity<SimpleUserDto> updateUserById(@PathVariable UUID userId, @RequestBody CreatUserByAdmin request) {
    SimpleUserDto userDto = adminService.updateUserById(userId, request);
    return ResponseEntity.ok(userDto);
  }

  @GetMapping("/users")
  public ResponseEntity<Page<SimpleUserDto>> getAllUsers(Pageable pageable){
    Page<SimpleUserDto> userDto=adminService.getAllUsers(pageable);
    return ResponseEntity.ok(userDto);
  }

  

}
