package com.utsdevelopers.vms.visitors;

public record VisitorCheckedEvent(
            String visitorEmail, String hostEmail, String firstName, String purpose, String tag, String phoneNumber) {
}
