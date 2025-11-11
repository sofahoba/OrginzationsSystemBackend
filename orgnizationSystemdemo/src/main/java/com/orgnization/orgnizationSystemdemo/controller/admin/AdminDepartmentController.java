package com.orgnization.orgnizationSystemdemo.controller.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orgnization.orgnizationSystemdemo.busenissLogic.specs.AdminService;
import com.orgnization.orgnizationSystemdemo.dto.department.DepartmentRequestDto;
import com.orgnization.orgnizationSystemdemo.dto.department.DepartmentResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/admin/departments")
@RequiredArgsConstructor
public class AdminDepartmentController {
  private final AdminService adminService;


  @PostMapping
  public ResponseEntity<DepartmentResponseDto> createDepartment(@RequestBody DepartmentRequestDto request){
    DepartmentResponseDto departmentDto=adminService.createDepartment(request);
    return ResponseEntity.ok(departmentDto);
  }

  @GetMapping("/{departmentId}")
  public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable UUID departmentId){
    DepartmentResponseDto departmentDto=adminService.getDepartmentById(departmentId);
    return ResponseEntity.ok(departmentDto);
  }

  @PutMapping("{departmentId}")
  public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable UUID departmentId,@RequestBody DepartmentRequestDto request){
    DepartmentResponseDto departmentDto=adminService.updateDepartment(departmentId, request);
    return ResponseEntity.ok(departmentDto);
  }

  @GetMapping
  public ResponseEntity<Page<DepartmentResponseDto>> getAllDepartments(Pageable pageable){
    Page<DepartmentResponseDto> departmentDto=adminService.getAllDepartments(pageable);
    return ResponseEntity.ok(departmentDto);
  }

  @DeleteMapping("/{departmentId}")
  public ResponseEntity<String> deleteDepartment(@PathVariable UUID departmentId){
    String response=adminService.deleteDepartment(departmentId);
    return ResponseEntity.ok(response);
  }
}
