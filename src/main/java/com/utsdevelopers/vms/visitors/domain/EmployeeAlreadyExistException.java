package com.utsdevelopers.vms.visitors.domain;

public class EmployeeAlreadyExistException extends RuntimeException{

    public EmployeeAlreadyExistException(String message) {
        super(message);
    }
}
