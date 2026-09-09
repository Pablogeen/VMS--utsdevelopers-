package com.utsdevelopers.vms.users.web;


import com.utsdevelopers.vms.users.UserResponse;
import com.utsdevelopers.vms.users.domain.LoginResponse;
import com.utsdevelopers.vms.users.domain.UserLoginRequest;
import com.utsdevelopers.vms.users.domain.UserRegisterRequest;
import com.utsdevelopers.vms.users.domain.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/users")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRegisterRequest registerRequest){
        log.info("Call made to register a user: {}",registerRequest.getEmail());
        UserResponse registeredUser = userService.registerUser(registerRequest);
        log.info("User has been registered successfully: {}",registeredUser);
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid UserLoginRequest loginRequest){
        log.info("Call made to login in a user: {}",loginRequest.getEmail());
        LoginResponse loggedInUser = userService.loginUser(loginRequest);
        log.info("User logged in successfully: ");
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }


    @GetMapping("/get-all-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserResponse>>getAllUsers(
                                                   @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size){
            log.info("Request made to get Users - page: {}, size: {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            List<UserResponse> requestedUsers = userService.getAllUsers(pageable);
            log.info("Requested users: requestedUsers : {}",requestedUsers);
            return new ResponseEntity<>(requestedUsers, HttpStatus.OK);

    }


    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse>getUserByEmail(@PathVariable String email) {
        log.info("Request made to get user by email: {}: ",email);
        UserResponse requestedUser = userService.getUserByEmail(email);
        log.info("User with email {} found ",email);
        return new ResponseEntity<>(requestedUser, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse>getUserById(@PathVariable Long id) {
        log.info("Request made to get user by ID: {}: ",id);
        UserResponse requestedUser = userService.getUserById(id);
        log.info("User with id {} found ",id);
        return new ResponseEntity<>(requestedUser, HttpStatus.OK);

    }



}
