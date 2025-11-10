package com.orgnization.orgnizationSystemdemo.busenissLogic.serviceImpl.tasks;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.orgnization.orgnizationSystemdemo.busenissLogic.specs.TaskService;
import com.orgnization.orgnizationSystemdemo.dto.tasks.TaskRequestDto;
import com.orgnization.orgnizationSystemdemo.dto.tasks.TaskResponseDto;
import com.orgnization.orgnizationSystemdemo.dto.tasks.UpdateTask;
import com.orgnization.orgnizationSystemdemo.model.entity.Task;
import com.orgnization.orgnizationSystemdemo.model.entity.Task.TaskStatus;
import com.orgnization.orgnizationSystemdemo.model.entity.User;
import com.orgnization.orgnizationSystemdemo.model.enums.SeniorityType;
import com.orgnization.orgnizationSystemdemo.repository.tasks.TaskRepository;
import com.orgnization.orgnizationSystemdemo.repository.user.UserRepository;
import com.orgnization.orgnizationSystemdemo.util.CurrentUser;
import com.orgnization.orgnizationSystemdemo.util.TaskMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  private final CurrentUser currentUser;



  @Override
  public TaskResponseDto createTask(TaskRequestDto request){
    /*
      - check if the assignd by user senioriryt level> assigned to create successfully
      - create new record in db 

     */

    UUID currUserId=currentUser.getCurrentUserId(userRepository);
    User assignedBy=userRepository.findById(currUserId).orElseThrow(()->new RuntimeException("user Not found"));
    User assignedTo= userRepository.findById(request.getAssignedToId()).orElseThrow(()->new RuntimeException("user not found"));

    if(!canAccess(assignedBy, assignedTo) || assignedBy.getDepartment().getId() != assignedTo.getDepartment().getId()){
      throw new RuntimeException("u can not assign tasks to high or equal seniorirty level or from another departmen");
    }

    Task task = new Task();
    task.setTitle(request.getTitle());
    task.setDescription(request.getDescription());
    task.setAssignedBy(assignedBy);
    task.setAssignedTo(assignedTo);
    task.setPriority(request.getPriority());
    task.setStatus(TaskStatus.PENDING);
    task.setDeadline(request.getDeadline());
    Task savedTask = taskRepository.save(task);
    return TaskMapper.toDto(savedTask);
  }


  @Override
  public TaskResponseDto updateTask(UUID taskId,UpdateTask request){
    UUID currUserId=currentUser.getCurrentUserId(userRepository);
    User currUser=userRepository.findById(currUserId).orElseThrow(()->new RuntimeException("user not found"));
    Task task=taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("task not found"));
    if(task.getAssignedBy().getId().equals(currUser.getId()) && task.getAssignedTo().getId().equals(currUser.getId())){
      throw new RuntimeException("task not acceptable for you to access");
    }
    if(task.getAssignedTo()==currUser){
      task.setStatus(request.getStatus());
    }
    else{
      if (request.getTitle() != null)
          task.setTitle(request.getTitle());

      if (request.getDescription() != null)
          task.setDescription(request.getDescription());

      if (request.getPriority() != null)
          task.setPriority(request.getPriority());

      if (request.getStatus() != null)
          task.setStatus(request.getStatus());

      if (request.getDeadline() != null)
          task.setDeadline(request.getDeadline());
      
      if(request.getAssignedToId()!=null){
        User newUser=userRepository.findById(request.getAssignedToId()).orElseThrow(()->new RuntimeException("user Not found"));
        if(!canAccess(currUser, newUser) || currUser.getDepartment().getId()!=newUser.getDepartment().getId()){
          throw new RuntimeException("You can not assign task to this user");
        }
        else{
          task.setAssignedTo(newUser);
        }
      }
    }
    Task savedTask=taskRepository.save(task);
    return TaskMapper.toDto(savedTask);
  }




  @Override 
  public void deleteTask(UUID taskId){
    Task task=taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("task not found"));
    UUID currUserId=currentUser.getCurrentUserId(userRepository);
    User currUser=userRepository.findById(currUserId).orElseThrow(()-> new RuntimeException("user not found"));
    if(task.getAssignedBy()!=currUser){
      throw new RuntimeException("U can not delete task not yours");
    }
    taskRepository.delete(task);
  }



  @Override 
  public Page<TaskResponseDto>getTasksAssignedByMe(Pageable pageable){
    
    UUID currUserId=currentUser.getCurrentUserId(userRepository);
    User currUser=userRepository.findById(currUserId).orElseThrow(()->new RuntimeException("user not found"));
    return taskRepository.findByAssignedBy(currUser, pageable)
        .map(TaskMapper::toDto);
  }


  @Override 
  public Page<TaskResponseDto>getTasksAssignedToMe(Pageable pageable){
    UUID currUserId=currentUser.getCurrentUserId(userRepository);
    User currUser=userRepository.findById(currUserId).orElseThrow(()->new RuntimeException("user not found"));
    return taskRepository.findByAssignedTo(currUser, pageable)
        .map(TaskMapper::toDto);
  }

  @Override 
  public TaskResponseDto getTaskById(UUID taskId){
    Task task=taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("Task not found"));
    return TaskMapper.toDto(task);
  } 


  private boolean canAccess(User assignedBy,User assignedTo){
    if(seniorityLevel(assignedBy.getType())>seniorityLevel(assignedTo.getType()))return true;
    return false;
  }

  private int seniorityLevel(SeniorityType type){
    return switch (type) {
      case ADMIN -> 6;
      case STAFF -> 5;
      case SENIOR -> 4;
      case JUNIOR -> 3;
      case FRESH -> 2;
      case INTERN -> 1;
    };
  }
}
