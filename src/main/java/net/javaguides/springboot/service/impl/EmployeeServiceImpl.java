package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    // we have created the EmployeeServiceImpl constructor with EmployeeRepository as an argument
    // @Autowired 생략 - 단일 생성자인 경우 @Autowired 어노테이션 사용할 필요 없다. (종속성을 자동으로 감지하고 주입)
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) { // 생성자 기반 종속성 주입 사용
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(savedEmployee.isPresent()) { // If a value is present, returns true, otherwise false.
            throw new ResourceNotFoundException("Employee already exist with given email:" + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }
}
