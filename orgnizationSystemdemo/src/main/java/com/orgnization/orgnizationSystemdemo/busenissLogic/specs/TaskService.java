package com.orgnization.orgnizationSystemdemo.busenissLogic.specs;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orgnization.orgnizationSystemdemo.dto.tasks.TaskRequestDto;
import com.orgnization.orgnizationSystemdemo.dto.tasks.TaskResponseDto;
import com.orgnization.orgnizationSystemdemo.dto.tasks.UpdateTask;

public interface TaskService {
  TaskResponseDto createTask(TaskRequestDto request);
  TaskResponseDto updateTask(UUID taskId,UpdateTask request);
  void deleteTask(UUID taskId);
  Page<TaskResponseDto>getTasksAssignedByMe(Pageable pageable);
  Page<TaskResponseDto>getTasksAssignedToMe(Pageable pageable);
  TaskResponseDto getTaskById(UUID taskId);
}
