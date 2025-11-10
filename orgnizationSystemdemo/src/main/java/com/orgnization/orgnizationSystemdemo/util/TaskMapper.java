package com.orgnization.orgnizationSystemdemo.util;

import com.orgnization.orgnizationSystemdemo.dto.tasks.TaskResponseDto;
import com.orgnization.orgnizationSystemdemo.dto.user.SimpleUserDto;
import com.orgnization.orgnizationSystemdemo.model.entity.Task;

public class TaskMapper {
  
  public static TaskResponseDto toDto(Task task) {
    if (task == null) return null;

    TaskResponseDto dto = new TaskResponseDto();
    dto.setId(task.getId());
    dto.setTitle(task.getTitle());
    dto.setDescription(task.getDescription());
    dto.setPriority(task.getPriority());
    dto.setStatus(task.getStatus());
    dto.setDeadline(task.getDeadline());
    dto.setCreatedAt(task.getCreatedAt());
    dto.setCompletedAt(task.getCompletedAt());

    // assignedBy
    SimpleUserDto assignedByDto = new SimpleUserDto();
    assignedByDto.setId(task.getAssignedBy().getId());
    assignedByDto.setFirstName(task.getAssignedBy().getFirstName());
    assignedByDto.setLastName(task.getAssignedBy().getLastName());
    assignedByDto.setEmail(task.getAssignedBy().getEmail());
    dto.setAssignedBy(assignedByDto);

    // assignedTo
    SimpleUserDto assignedToDto = new SimpleUserDto();
    assignedToDto.setId(task.getAssignedTo().getId());
    assignedToDto.setFirstName(task.getAssignedTo().getFirstName());
    assignedToDto.setLastName(task.getAssignedTo().getLastName());
    assignedToDto.setEmail(task.getAssignedTo().getEmail());
    dto.setAssignedTo(assignedToDto);

    return dto;
}
    
}
