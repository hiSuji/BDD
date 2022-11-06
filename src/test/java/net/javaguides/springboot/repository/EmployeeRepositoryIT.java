package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // @DataJpaTest annotation it internally uses in-memory database for testing.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Disable the in-memory(H2) database. and this by default will take MySQL.
public class EmployeeRepositoryIT {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;


    // 모든 테스트에서 Employee 중복
    // @BeforeEach method should be executed before each @Test method
    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("Hazel")
                .lastName("Tree")
                .email("HazelTree@coffee.com")
                .build();
    }


    // employee 저장 - public void given_when_then() : 명명규칙
    @DisplayName("Employee 저장 테스트")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given - precondition or setup

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

        employeeRepository.save(employee);
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
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("weather@sunny.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("weather@sunny.com");
    }

    // Junit test for delete employee operation
    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        // employeeRepository.delete(employeeDB);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    // Junit test for custom query using JPQL with index
    @DisplayName("Junit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());


        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for custom query using JPQL with Named params
    @DisplayName("Junit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());


        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for custom query using native SQL with index
    @DisplayName("Junit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());


        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for custom query using native SQL with Named params
    @DisplayName("Junit test for custom query using native SQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamed_thenReturnEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());


        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}
