package com.orgnization.orgnizationSystemdemo.repository.dpartment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orgnization.orgnizationSystemdemo.model.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
  
}
