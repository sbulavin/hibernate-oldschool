package com.examples.hibernate.oldschool.dao;


import com.examples.hibernate.oldschool.entities.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee getEmployeeById(int employeeId);

    List<Employee> getAllEmployees();

    int saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployee(int employeeId);
}
