package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class EmployeeServiceTests {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach // this method would be basically executed before each JUnit test case within this class.
    public void setup() {

        // We can use Mockito class mock() method to create a mock object of a given class or interface.
        // using Mockito.mock() method - import org.mockito.Mockito;
//        employeeRepository = Mockito.mock(EmployeeRepository.class);

        employeeRepository = mock(EmployeeRepository.class); // import static org.mockito.Mockito.*;

        employeeService = new EmployeeServiceImpl(employeeRepository);  // 생성자 기반 종속성 주입
    }

    // Junit test for saveEmployee method
    @DisplayName("Junit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Hazel")
                .lastName("Tree")
                .email("HazelTree@coffee.com")
                .build();
        
        //saveEmployee() 메서드 > findByEmail 테스트
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty()); // Optional.empty() : 비어있는(null) Optional 객체를 가져온다.
        //saveEmployee() 메서드 > save() 테스트
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee);

        // then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
    }
}
