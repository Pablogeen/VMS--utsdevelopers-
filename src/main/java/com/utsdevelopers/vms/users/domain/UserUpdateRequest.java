package com.utsdevelopers.vms.users.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateRequest {

    private String firstname;

    private String lastname;

    private String password;

    @Pattern(
            regexp = "^(ADMIN|RECEPTIONIST)$",
            message = "Role must be either ADMIN or RECEPTIONIST")
    private String  role;

}
