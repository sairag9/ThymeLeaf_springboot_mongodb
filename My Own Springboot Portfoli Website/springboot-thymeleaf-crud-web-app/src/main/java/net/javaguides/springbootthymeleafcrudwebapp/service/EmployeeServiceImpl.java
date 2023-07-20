package net.javaguides.springbootthymeleafcrudwebapp.service;

import jakarta.transaction.Transactional;
import net.javaguides.springbootthymeleafcrudwebapp.model.Employee;
import net.javaguides.springbootthymeleafcrudwebapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void updateEmployee(Long id, Employee updatedEmployee) {
        // Fetch the existing employee from the database
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);

        if (existingEmployee != null) {
            // Update the existing employee with the new data
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());

            // Save the updated employee to the database
            employeeRepository.save(existingEmployee);
        }
    }
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

}
