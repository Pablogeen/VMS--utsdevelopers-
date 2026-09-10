package com.utsdevelopers.vms.visitors.domain;

import com.utsdevelopers.vms.users.User;
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
@Table(name = "visitors")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "company", length = 255)
    private String company;

    @Lob
    @Column(name = "purpose", nullable = false, columnDefinition = "TEXT")
    private String purpose;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "host_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_visitors_host"))
    private Employee host;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_receptionist"))
    private User user;

    @Column(name = "tag")
    private String tag;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private Status status;

    @Column(name = "checked_in_time")
    private LocalDateTime checkedInTime;

    @Column(name = "checked_out_time")
    private LocalDateTime checkedOutTime;
}

