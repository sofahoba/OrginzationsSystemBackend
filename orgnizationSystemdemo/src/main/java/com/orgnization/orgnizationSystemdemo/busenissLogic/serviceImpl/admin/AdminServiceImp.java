package com.orgnization.orgnizationSystemdemo.busenissLogic.serviceImpl.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orgnization.orgnizationSystemdemo.busenissLogic.specs.AdminService;
import com.orgnization.orgnizationSystemdemo.dto.department.DepartmentRequestDto;
import com.orgnization.orgnizationSystemdemo.dto.department.DepartmentResponseDto;
import com.orgnization.orgnizationSystemdemo.dto.user.CreatUserByAdmin;
import com.orgnization.orgnizationSystemdemo.dto.user.SimpleUserDto;
import com.orgnization.orgnizationSystemdemo.model.entity.Department;
import com.orgnization.orgnizationSystemdemo.model.entity.DepartmentRole;
import com.orgnization.orgnizationSystemdemo.model.entity.User;
import com.orgnization.orgnizationSystemdemo.repository.dpartment.DepartmentRepository;
import com.orgnization.orgnizationSystemdemo.repository.dpartment.DepartmentRoleRepository;
import com.orgnization.orgnizationSystemdemo.repository.user.UserRepository;
import com.orgnization.orgnizationSystemdemo.util.DepartmentMapper;
import com.orgnization.orgnizationSystemdemo.util.TaskMapper;
import com.orgnization.orgnizationSystemdemo.util.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {
  
  private final UserRepository userRepository;
  private final DepartmentRepository departmentRepository;
  private final DepartmentRoleRepository departmentRoleRepository;


  // ***********************user management**********************
  
  @Override
  public SimpleUserDto getUserById(UUID userId) {
    User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
    return UserMapper.toDto(user);
  }

  @Override
  public SimpleUserDto createUser(CreatUserByAdmin request) {
    
    Department department= departmentRepository.findById(request.getDepartmentId()).orElseThrow(()->new RuntimeException("Department not found"));

    DepartmentRole departmentRole= departmentRoleRepository.findById(request.getDepartmentRoleId())
      .orElseThrow(()->new RuntimeException("Department Role not found"));
    User user=new User();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setType(request.getType());
    user.setDepartment(department);
    user.setDepartmentRole(departmentRole);
    User savedUser=userRepository.save(user);
    return UserMapper.toDto(savedUser);
  }

  @Override
  public String deleteUserById(UUID userId) {
    User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
    userRepository.delete(user);
    return "User deleted successfully";
  }

  @Override
  public SimpleUserDto updateUserById(UUID userId, CreatUserByAdmin request) {
    User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
    
    if(request.getFirstName()!=null)
      user.setFirstName(request.getFirstName());
    if(request.getLastName()!=null)
      user.setLastName(request.getLastName());
    if(request.getEmail()!=null)
      user.setEmail(request.getEmail());
    if(request.getPassword()!=null)
      user.setPassword(request.getPassword());
    if(request.getType()!=null)
      user.setType(request.getType());
    if(request.getDepartmentId()!=null) {
      Department department= departmentRepository.findById(request.getDepartmentId())
        .orElseThrow(()->new RuntimeException("Department not found"));
      user.setDepartment(department);
    }
    if(request.getDepartmentRoleId()!=null) {
      DepartmentRole departmentRole= departmentRoleRepository.findById(request.getDepartmentRoleId())
        .orElseThrow(()->new RuntimeException("Department Role not found"));
      user.setDepartmentRole(departmentRole);
    }
    User updatedUser=userRepository.save(user);
    return UserMapper.toDto(updatedUser);
  }

  @Override
  public Page<SimpleUserDto> getAllUsers(Pageable pageable) {
    Page<User> user=userRepository.findAll(pageable);
    return user.map(UserMapper::toDto);
  }


  // ***********************department management**********************

  @Override
  public DepartmentResponseDto createDepartment(DepartmentRequestDto request){
    
    Department department=new Department();
    department.setName(request.getName());
    department.setDescription(request.getDescription());
    Department savedDepartment=departmentRepository.save(department);
    return DepartmentMapper.toDto(savedDepartment);
  }

  @Override
  public DepartmentResponseDto updateDepartment(UUID departmentId, DepartmentRequestDto request){
    Department department=departmentRepository.findById(departmentId)
      .orElseThrow(()->new RuntimeException("Department not found"));
    
    if(request.getName()!=null)
      department.setName(request.getName());
    if(request.getDescription()!=null)
      department.setDescription(request.getDescription());
    
    Department updatedDepartment=departmentRepository.save(department);
    return DepartmentMapper.toDto(updatedDepartment);
  }

  @Override
  public String deleteDepartment(UUID departmentId){
    Department department=departmentRepository.findById(departmentId)
      .orElseThrow(()->new RuntimeException("Department not found"));
    departmentRepository.delete(department);
    return "Department deleted successfully";
  }

  @Override
  public Page<DepartmentResponseDto> getAllDepartments(Pageable pageable){
    return departmentRepository.findAll(pageable)
        .map(DepartmentMapper::toDto);
  }

  @Override
  public DepartmentResponseDto getDepartmentById(UUID departmentId){
    Department department=departmentRepository.findById(departmentId)
      .orElseThrow(()->new RuntimeException("Department not found"));
    return DepartmentMapper.toDto(department);
  }
}
