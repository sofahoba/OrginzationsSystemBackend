package com.orgnization.orgnizationSystemdemo.dto.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import com.orgnization.orgnizationSystemdemo.dto.user.SimpleUserDto;
import com.orgnization.orgnizationSystemdemo.model.entity.Task;

import lombok.Data;

@Data
public class TaskResponseDto {
  private UUID id;
  private String title;
  private String description;
  private SimpleUserDto assignedBy;
  private SimpleUserDto assignedTo;
  private Task.Priority priority;
  private Task.TaskStatus status;
  private LocalDateTime deadline;
  private LocalDateTime createdAt;
  private LocalDateTime completedAt;
}
