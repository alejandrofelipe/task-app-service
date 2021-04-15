package com.example.task.service;

import com.example.task.exception.ExistingIdObjectException;
import com.example.task.exception.MissingIdObjectException;
import com.example.task.model.Employee;
import com.example.task.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends AbstractService<Employee, Long, EmployeeRepo> {
    public EmployeeService(EmployeeRepo repository) {
        super(repository);
    }

    public Employee add(Employee employee) {
        boolean isNew = true;
        if (employee.getId() != null)
            isNew = !exists(employee.getId());
        if (!isNew)
            throw new ExistingIdObjectException("Already exist a responsible with id(" + employee.getId() + ").");
        return save(employee);
    }

    public Employee update(Employee employee) {
        if (employee.getId() == null)
            throw new MissingIdObjectException("ID is required to update a responsible.");
        return save(employee);
    }
}
