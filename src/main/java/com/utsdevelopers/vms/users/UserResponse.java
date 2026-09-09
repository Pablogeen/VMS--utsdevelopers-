package com.utsdevelopers.vms.users;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse{
    private Long id;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}


