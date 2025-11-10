package com.orgnization.orgnizationSystemdemo.repository.tasks;

import org.springframework.stereotype.Repository;

import com.orgnization.orgnizationSystemdemo.model.entity.Task;
import com.orgnization.orgnizationSystemdemo.model.entity.User;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TaskRepository extends JpaRepository<Task,UUID>{
  Page<Task> findByAssignedBy(User assignedBy, Pageable pageable);

  Page<Task> findByAssignedTo(User assignedTo, Pageable pageable);
}
