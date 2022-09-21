package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class) // 주석 없으면 NullPointerException 발생 :  테스트 클래스가 Mockito를 사용한다는 것을 알림
public class EmployeeServiceTests {

    @Mock // We can use @Mock to create and inject mocked instances without having to call Mockito.mock manually
    private EmployeeRepository employeeRepository; // @Mock 어노테이션 사용 -> employeeRepository = mock(EmployeeRepository.class); 주석처리

//    private EmployeeService employeeService; // 주석 -> employeeService = new EmployeeServiceImpl(employeeRepository); 주석처리
    @InjectMocks // @InjectMocks creates the mock object of the class and injects the mocks that are marked with the annotations @Mock into it.
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach // this method would be basically executed before each JUnit test case within this class.
    public void setup() {

        // We can use Mockito class mock() method to create a mock object of a given class or interface.
        // using Mockito.mock() method - import org.mockito.Mockito;
//        employeeRepository = Mockito.mock(EmployeeRepository.class);

//        employeeRepository = mock(EmployeeRepository.class); // import static org.mockito.Mockito.*;

//        employeeService = new EmployeeServiceImpl(employeeRepository);  // 생성자 기반 종속성 주입

         employee = Employee.builder()
                .id(1L)
                .firstName("Hazel")
                .lastName("Tree")
                .email("HazelTree@coffee.com")
                .build();
    }

    // Junit test for saveEmployee method
    @DisplayName("Junit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - precondition or setup
        //saveEmployee() 메서드 > findByEmail 테스트
        //willReturn() 반환하는 값
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty()); // Optional.empty() : 비어있는(null) Optional 객체를 가져온다.
        //saveEmployee() 메서드 > save() 테스트
        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee);

        // then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
    }
}
