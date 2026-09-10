package com.utsdevelopers.vms.visitors.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeResponse addEmployee(EmployeeRequest request) {
        log.info("Adding employee with email: {}", request.getEmail());

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmployeeAlreadyExistException("Employee with this email already exists");
        }

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .department(request.getDepartment())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeResponse.class);
    }


    public EmployeeResponse getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + id));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .toList();
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        log.info("Updating employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + id));

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setDepartment(request.getDepartment());

        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Employee updated successfully: {}", id);

        return modelMapper.map(updatedEmployee, EmployeeResponse.class);
    }

    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
        log.info("Employee deleted successfully: {}", id);
    }

    public EmployeeResponse searchEmployee(String email) {
        log.info("Searching for employee with email: {}", email);
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() ->
                        new RuntimeException("Employee not found with email: " + email));
        return modelMapper.map(employee, EmployeeResponse.class);
}

    public long getTotalEmployees() {
        return employeeRepository.count();
    }
}
