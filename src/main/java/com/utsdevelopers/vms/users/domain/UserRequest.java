package com.utsdevelopers.vms.users.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {


    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(
            regexp = "^(ADMIN|RECEPTIONIST)$",
            message = "Role must be either ADMIN or RECEPTIONIST")
    private String  role;

}
