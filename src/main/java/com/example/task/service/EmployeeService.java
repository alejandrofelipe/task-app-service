package com.example.task.service;

import com.example.task.model.Employee;
import com.example.task.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends CustomService<Employee, Long, EmployeeRepo> {
    public EmployeeService(EmployeeRepo repository) {
        super(repository);
    }


}
