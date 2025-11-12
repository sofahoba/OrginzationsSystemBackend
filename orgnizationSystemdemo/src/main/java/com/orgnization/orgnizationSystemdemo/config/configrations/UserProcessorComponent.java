package com.orgnization.orgnizationSystemdemo.config.configrations;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.orgnization.orgnizationSystemdemo.dto.csv.UserCSV;
import com.orgnization.orgnizationSystemdemo.model.entity.Department;
import com.orgnization.orgnizationSystemdemo.model.entity.User;
import com.orgnization.orgnizationSystemdemo.model.enums.SeniorityType;
import com.orgnization.orgnizationSystemdemo.repository.dpartment.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserProcessorComponent implements ItemProcessor<UserCSV, User> {
    
    private final DepartmentRepository departmentRepository;

    @Override
    public User process(UserCSV userCSV) throws Exception {
        User user = new User();
        user.setEmail(userCSV.getEmail());
        user.setPassword(userCSV.getPassword());
        user.setFirstName(userCSV.getFirstName());
        user.setLastName(userCSV.getLastName());
        user.setActive(userCSV.getActive());
        
        if (userCSV.getType() != null) {
            user.setType(SeniorityType.valueOf(userCSV.getType().toUpperCase()));
        }
        if (userCSV.getDepartment_id() != null && !userCSV.getDepartment_id().isEmpty()) {
            UUID departmentId = UUID.fromString(userCSV.getDepartment_id());
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new RuntimeException("Department not found: " + departmentId));
            user.setDepartment(department);
        }
        
        return user;
    }
}
