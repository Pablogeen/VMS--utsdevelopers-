package com.utsdevelopers.vms.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", length = 50)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
