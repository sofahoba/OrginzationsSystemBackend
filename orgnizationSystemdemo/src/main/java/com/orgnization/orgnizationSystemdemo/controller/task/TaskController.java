package com.orgnization.orgnizationSystemdemo.controller.task;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orgnization.orgnizationSystemdemo.busenissLogic.specs.TaskService;
import com.orgnization.orgnizationSystemdemo.dto.tasks.TaskRequestDto;
import com.orgnization.orgnizationSystemdemo.dto.tasks.TaskResponseDto;
import com.orgnization.orgnizationSystemdemo.dto.tasks.UpdateTask;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
  
  private final TaskService taskService;

  @PostMapping
  public ResponseEntity<TaskResponseDto>createTask(@RequestBody TaskRequestDto request){
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
  }

  @PutMapping("/{taskId}")
  public ResponseEntity<TaskResponseDto> updateTask(@PathVariable UUID taskId, @Valid @RequestBody UpdateTask request) {
    TaskResponseDto updatedTask = taskService.updateTask(taskId, request);
    return ResponseEntity.ok(updatedTask);
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
    taskService.deleteTask(taskId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{taskId}")
  public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID taskId) {
    TaskResponseDto task = taskService.getTaskById(taskId);
    return ResponseEntity.ok(task);
  }

  @GetMapping("/assigned-by-me")
  public ResponseEntity<Page<TaskResponseDto>> getTasksAssignedByMe(Pageable pageable) {
    Page<TaskResponseDto> tasks = taskService.getTasksAssignedByMe(pageable);
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/assigned-to-me")
  public ResponseEntity<Page<TaskResponseDto>> getTasksAssignedToMe(Pageable pageable) {
    Page<TaskResponseDto> tasks = taskService.getTasksAssignedToMe(pageable);
    return ResponseEntity.ok(tasks);
  }

}
