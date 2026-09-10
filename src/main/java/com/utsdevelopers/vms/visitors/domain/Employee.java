package com.utsdevelopers.vms.visitors.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "first_name", nullable = false, length = 100)
        private String firstName;

        @Column(name = "last_name", nullable = false, length = 100)
        private String lastName;

        @Column(name = "email", nullable = false, unique = true, length = 254)
        private String email;

        @Column(name = "phone_number", nullable = false, unique = true,  length = 30)
        private String phoneNumber;

        @Column(name = "department", nullable = false, length = 100)
        private String department;

}
