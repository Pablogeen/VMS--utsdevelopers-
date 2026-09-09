package com.utsdevelopers.vms.users.domain;

public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
