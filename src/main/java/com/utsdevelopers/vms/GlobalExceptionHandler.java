package com.utsdevelopers.vms;

import com.utsdevelopers.vms.users.domain.EmailAlreadyExistException;
import com.utsdevelopers.vms.users.domain.InvalidCredentialsException;
import com.utsdevelopers.vms.users.domain.UserNotFoundException;
import com.utsdevelopers.vms.visitors.domain.EmployeeAlreadyExistException;
import com.utsdevelopers.vms.visitors.domain.EmployeeNotFoundException;
import com.utsdevelopers.vms.visitors.domain.NoTargsAvailableException;
import com.utsdevelopers.vms.visitors.domain.VisitorNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> genericException(Exception e, WebRequest request) {
        log.error("Generic error");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "INTERNAL SERVER ERROR",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Handling Invalid Request");
        String message = Optional.ofNullable(ex.getBindingResult().getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("Validation error");

        String details = request.getDescription(false);

        ErrorDetails errorDetails = new ErrorDetails(message, "INVALID REQUEST",details, LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> emailAlreadyExistException(EmailAlreadyExistException e, WebRequest request) {
        log.error("Email already exist error");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "EMAIL ALREADY TAKEN",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException e, WebRequest request) {
        log.error("User not found exception");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "USER NOT FOUND",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> emailAlreadyExistException(InvalidCredentialsException e, WebRequest request) {
        log.error("Invalid credentials exception");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "INVALID CREDENTIALS",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);

    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException e, WebRequest request) {
        log.error("Access Denied Exception");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "UNAUTHORIZED",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.FORBIDDEN);

    }


    @ExceptionHandler(NoTargsAvailableException.class)
    public ResponseEntity<?> tagsUnavailableException(NoTargsAvailableException e, WebRequest request) {
        log.error("No Tags Available Exception");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "NO TAGS AVAILABLE",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(EmployeeAlreadyExistException.class)
    public ResponseEntity<?> employeeAlreadyExistException(EmployeeAlreadyExistException e, WebRequest request) {
        log.error("Employee Already Exist Exception");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "EMPLOYEE ALREADY EXIST",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> employeeNotFoundException(EmployeeNotFoundException e, WebRequest request) {
        log.error("Employee Not Found Exception");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "EMPLOYEE NOT FOUND",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(VisitorNotFoundException.class)
    public ResponseEntity<?> visitorNotFoundException(VisitorNotFoundException e, WebRequest request) {
        log.error("Visitor Not Exception");
        ErrorDetails details = new ErrorDetails(
                e.getMessage(),
                "VISITOR WITH TAG NOT FOUND",
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);

    }
}
