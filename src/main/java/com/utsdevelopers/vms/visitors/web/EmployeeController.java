package com.utsdevelopers.vms.visitors.web;


import com.utsdevelopers.vms.visitors.domain.EmployeeRequest;
import com.utsdevelopers.vms.visitors.domain.EmployeeResponse;
import com.utsdevelopers.vms.visitors.domain.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest request){
        log.info("Request made to add an employee: {}: ", request.getFirstName());
        EmployeeResponse response = employeeService.addEmployee(request);
        log.info("Employee added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        log.info("Request made to get employee: {}", id);
        EmployeeResponse response = employeeService.getEmployeeById(id);
        log.info("Employee retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','RECEPTIONIST')")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        log.info("Request made to get all employees");
        List<EmployeeResponse> response = employeeService.getAllEmployees();
        log.info("Employees retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,
                                                           @RequestBody EmployeeRequest request) {
        log.info("Request made to update employee: {}", id);
        EmployeeResponse response = employeeService.updateEmployee(id, request);
        log.info("Employee updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("Request made to delete employee: {}", id);
        employeeService.deleteEmployee(id);
        log.info("Employee deleted successfully");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeeResponse> searchEmployee(@RequestParam String keyword) {
        log.info("Request made to search employees with keyword: {}", keyword);
        EmployeeResponse response = employeeService.searchEmployee(keyword);
        log.info("Employee search completed successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stats/total")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Long> getTotalEmployees() {
        log.info("Request made to get total number of employees");
        long response = employeeService.getTotalEmployees();
        log.info("Total employees: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

