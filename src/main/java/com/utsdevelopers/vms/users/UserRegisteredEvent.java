package com.utsdevelopers.vms.users;

public record UserRegisteredEvent(String email, String token) {
}
