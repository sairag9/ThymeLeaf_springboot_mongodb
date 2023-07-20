package net.javaguides.springbootthymeleafcrudwebapp.service;

import net.javaguides.springbootthymeleafcrudwebapp.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    void addEmployee(Employee employee);
    void deleteEmployee(Long id);
    void updateEmployee(Long id, Employee updatedEmployee);
    Employee getEmployeeById(Long id);
}
