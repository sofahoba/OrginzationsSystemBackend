package com.orgnization.orgnizationSystemdemo.dto.csv;

import lombok.Data;

@Data
public class UserCSV {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean active;
    private String type;
    private String department_id;
}
