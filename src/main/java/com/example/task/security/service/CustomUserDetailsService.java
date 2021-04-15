package com.example.task.security.service;

import com.example.task.exception.ObjectNotFoundException;
import com.example.task.model.Employee;
import com.example.task.service.EmployeeService;
import org.springframework.data.domain.Example;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeService employeeService;

    public CustomUserDetailsService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            Employee employee = employeeService.get(Example.of(
                    Employee.builder().username(s).build()
            ));
            // {noop} => plain text password encode
            return new User(employee.getUsername(), employee.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("USER")));
        } catch (ObjectNotFoundException e) {
            throw new UsernameNotFoundException("Username " + s + " was not found.");
        }

    }

}
