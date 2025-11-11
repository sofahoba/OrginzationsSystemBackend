package com.orgnization.orgnizationSystemdemo.repository.dpartment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orgnization.orgnizationSystemdemo.model.entity.DepartmentRole;

@Repository
public interface DepartmentRoleRepository extends JpaRepository<DepartmentRole, UUID> {
  
}
