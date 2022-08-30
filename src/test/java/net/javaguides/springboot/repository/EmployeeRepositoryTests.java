package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    // employee 저장 - public void given_when_then() : 명명규칙
    @DisplayName("Employee 저장 테스트")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Hazel")
                .lastName("Tree")
                .email("HazelTree@coffee.com")
                .build();
        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // Junit test for get all employees operation
    @DisplayName("Junit test for get all employees operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Hazel")
                .firstName("Hazel")
                .lastName("Tree")
                .email("HazelTree@coffee.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Coffee")
                .lastName("Latte")
                .email("CoffeeLatte@coffee.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("Candy")
                .lastName("Sweet")
                .email("CandySweet@coffee.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(3);
    }

    // Junit test for get employee by id operation
    @Test
    public void givenEmployeeObject_whenFindByID_thenReturnEmployeeObject() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("orange")
                .lastName("lemon")
                .email("orange@lemon.com")
                .build();

        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test

        // The get() method of java.util.Optional class in Java is used to get the value of this Optional instance.
        // If there is no value present in this Optional instance, then this method throws NullPointerException.
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // Junit test for get employee by email operation
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("iceTea")
                .lastName("hot")
                .email("iceTea@hot.com")
                .build();

        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        Employee employee4 = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employee4).isNotNull();
    }

    // Junit test for update employee operation
    @DisplayName("Junit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployee() {
        // given - precondition or setup
        Employee employeeDB = Employee.builder()
                .firstName("rainbow")
                .lastName("sunny")
                .email("rainbow@sunny.com")
                .build();

        employeeRepository.save(employeeDB);
        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employeeDB.getId()).get();
        savedEmployee.setEmail("weather@sunny.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("weather@sunny.com");
    }

}
