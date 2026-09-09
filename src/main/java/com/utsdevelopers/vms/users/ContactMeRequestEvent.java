package com.utsdevelopers.vms.users;


public record ContactMeRequestEvent(String email,  String phoneNumber, String reasonForContact,String message){}


