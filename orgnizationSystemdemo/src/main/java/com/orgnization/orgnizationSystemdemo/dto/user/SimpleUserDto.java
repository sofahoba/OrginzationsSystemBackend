package com.orgnization.orgnizationSystemdemo.dto.user;

import java.util.UUID;

import lombok.Data;

@Data
public class SimpleUserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
