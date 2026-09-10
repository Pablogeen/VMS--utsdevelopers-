package com.utsdevelopers.vms.visitors.domain;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
}
