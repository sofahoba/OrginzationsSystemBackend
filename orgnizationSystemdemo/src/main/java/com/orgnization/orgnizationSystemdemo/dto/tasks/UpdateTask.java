package com.orgnization.orgnizationSystemdemo.dto.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import com.orgnization.orgnizationSystemdemo.model.entity.Task.Priority;
import com.orgnization.orgnizationSystemdemo.model.entity.Task.TaskStatus;

import lombok.Data;

@Data
public class UpdateTask {
  private String title;
  private String description;
  private UUID assignedToId;
  private Priority priority;
  private TaskStatus status;
  private LocalDateTime deadline;
}

