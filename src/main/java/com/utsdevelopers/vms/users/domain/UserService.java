package com.utsdevelopers.vms.users.domain;


import com.utsdevelopers.vms.users.Role;
import com.utsdevelopers.vms.users.User;
import com.utsdevelopers.vms.users.UserResponse;
import com.utsdevelopers.vms.users.security.JwtHelper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JwtHelper jwtHelper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UserResponse registerUser(@Valid UserRequest registerRequest) {
        log.info("About to register");

        String email = registerRequest.getEmail();
        log.info("Email {}",email);

        boolean userExisted = userRepo.findByEmail(email.strip()).isPresent();

        if(userExisted){
            throw new EmailAlreadyExistException("EMAIL ALREADY TAKEN");
        }

        User mappedUser = modelMapper.map(registerRequest, User.class);
        log.info("Mapped user into entity");

        mappedUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        log.info("Encoded password");

        mappedUser.setCreatedAt(LocalDateTime.now());

        mappedUser.setRole(Role.valueOf(registerRequest.getRole().toUpperCase()));
        log.info("Role has been set to user:");

        User savedUser = userRepo.save(mappedUser);
        log.info("User has been saved");


//        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser.getEmail(), token));

        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        log.info("User response: {}",userResponse);

        return userResponse;
    }

    public LoginResponse loginUser(@Valid UserLoginRequest loginRequest) {

        var user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("EMAIL NOT FOUND"));
        log.info("Login attempt for user: {}", loginRequest.getEmail());

        try {
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail().strip(), loginRequest.getPassword().strip());
            manager.authenticate(authentication);
            var jwtToken = jwtHelper.generateToken(user);
            return new LoginResponse(jwtToken.token(), jwtToken.expiresAt(), user.getEmail(), user.getRole().name());
        }catch(BadCredentialsException ex){
            throw new InvalidCredentialsException("INVALID CREDENTIALS");
        }
    }

    @Transactional
    public UserResponse getUserById(Long id) {
        log.info("Getting user with id: {}",id);
        User user = userRepo.findById(id).
                orElseThrow(() -> new UserNotFoundException("USER NOT FOUND"));

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        log.info("Mapped user to userResponse: {}",userResponse);
        return userResponse;
    }


    @Transactional
    public List<UserResponse> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable)
                .stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
    }

    public long getTotalUsers() {
        return userRepo.count();
    }

    public void deleteUser(Long id) {
        log.info("Deleting user: {}", id);
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepo.delete(user);
        log.info("User deleted successfully: {}", id);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        log.info("Updating user: {}", id);
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(request.getEmail());
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User updatedUser = userRepo.save(user);
        log.info("User updated successfully: {}", id);
        return modelMapper.map(updatedUser, UserResponse.class);
    }
}

