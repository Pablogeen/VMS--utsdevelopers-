package com.utsdevelopers.vms.users.security;

import java.time.Instant;

public record JwtToken(String token, Instant expiresAt) {}
