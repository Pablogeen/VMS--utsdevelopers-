package com.utsdevelopers.vms.users.domain;

import java.time.Instant;

public record LoginResponse(String token, Instant expiresAt, String email, String role) {}

