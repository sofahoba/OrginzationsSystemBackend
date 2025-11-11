package com.orgnization.orgnizationSystemdemo.busenissLogic.specs;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orgnization.orgnizationSystemdemo.dto.department.DepartmentRequestDto;
import com.orgnization.orgnizationSystemdemo.dto.department.DepartmentResponseDto;
import com.orgnization.orgnizationSystemdemo.dto.user.CreatUserByAdmin;
import com.orgnization.orgnizationSystemdemo.dto.user.SimpleUserDto;

public interface AdminService{
  // user management
  SimpleUserDto getUserById(UUID userId);
  SimpleUserDto createUser(CreatUserByAdmin request);
  String deleteUserById(UUID userId);
  Page<SimpleUserDto> getAllUsers(Pageable pageable);
  SimpleUserDto updateUserById(UUID userId,CreatUserByAdmin request);
  
  // department management 
  DepartmentResponseDto createDepartment(DepartmentRequestDto request);
  DepartmentResponseDto updateDepartment(UUID departmentId,DepartmentRequestDto request);
  String deleteDepartment(UUID departmentId);
  Page<DepartmentResponseDto> getAllDepartments(Pageable pageable);
  DepartmentResponseDto getDepartmentById(UUID departmentId);
  
  
}
