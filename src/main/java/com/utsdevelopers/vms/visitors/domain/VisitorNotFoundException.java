package com.utsdevelopers.vms.visitors.domain;

public class VisitorNotFoundException extends RuntimeException{

    public VisitorNotFoundException(String message) {
        super(message);
    }
}
