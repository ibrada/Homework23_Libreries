package org.example.service;

import org.apache.commons.lang3.StringUtils;
import org.example.exception.EmployeeAlreadyAddedException;
import org.example.exception.EmployeeIncorrectInputException;
import org.example.exception.EmployeeNotFoundException;
import org.example.exception.EmployeeStoragelsFullException;
import org.example.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    private static final int MAX_EMPLOYEES = 10;
    private final List<Employee> employees = new ArrayList<>();

    public void add(String firstName, String lastName) {
        boolean check = StringUtils.isAlpha(firstName) && StringUtils.isAlpha(lastName);
        if (!check) {
            throw new EmployeeIncorrectInputException();
        }
        if (employees.size() == MAX_EMPLOYEES) {
            throw new EmployeeStoragelsFullException();
        }
        firstName = StringUtils.capitalize(firstName.toLowerCase());
        lastName = StringUtils.capitalize(lastName.toLowerCase());

        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.add(employee);
    }

    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.remove(employee)) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public List<Employee> findAll() {
        return Collections.unmodifiableList(employees);
    }

}
