package com.utsdevelopers.vms.visitors.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String company;

    private String purpose;

    private Long hostId;

    private Long userId;

    private String tag;

    private Status status;

    private LocalDateTime checkedInTime;

    private LocalDateTime checkedOutTime;
}