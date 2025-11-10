package com.orgnization.orgnizationSystemdemo.dto.tasks;

import java.time.LocalDateTime;

import com.orgnization.orgnizationSystemdemo.model.entity.Task.Priority;
import com.orgnization.orgnizationSystemdemo.model.entity.Task.TaskStatus;

import lombok.Data;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

@Data
public class TaskRequestDto {
  @NotNull(message = "Title is required")
  private String title;
  
  private String description;
  
  @NotNull(message = "Assigned user ID is required")
  private UUID assignedToId;
  
  @NotNull(message = "Priority is required")
  private Priority priority;
  
  private TaskStatus status;
  
  @NotNull(message = "Deadline is required")
  private LocalDateTime deadline;
}
