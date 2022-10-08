package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;
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
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for saveEmployee method
    // ResourceNotFoundException 발생시키는 saveEmployee() 테스트
    @DisplayName("Junit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given - precondition or setup
        //saveEmployee() 메서드 > findByEmail 테스트
        //willReturn() 반환하는 값
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee)); // Returns an Optional describing the given non-null value.
        //saveEmployee() 메서드 > save() 테스트
//        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when - action or the behaviour that we are going test
        // assertThrows : 첫번째 인자로 발생할 예외 클래스의 Class 타입을 받는다. executable을 실행하여 예외가 발생할 경우 Class 타입과 발생된 Exception이 같은타입인지 확인
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));
//        Employee savedEmployee = employeeService.saveEmployee(employee);
//
//        System.out.println(savedEmployee);

        // then - verify the output
//        Assertions.assertThat(savedEmployee).isNotNull();
        verify(employeeRepository, never()).save(any(Employee.class)); // return employeeRepository.save(employee) 로직 호출 여부 검증
        // verify(mock).method(param); - 해당 Mock Object의 메소드를 호출했는지 검증
        // verify(mock, never()).method(param); - 해당 Mock Object의 메소드가 호출이 안됬는지 검증
    }

    // Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Chris")
                .lastName("Evans")
                .email("ChrisEvans@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1)); // List.of() - Returns an unmodifiable list

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method (negative scenario")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Chris")
                .lastName("Evans")
                .email("ChrisEvans@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList()); // List.of() - Returns an unmodifiable list

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
    }

    // Junit test for getEmployeeById method
    @DisplayName("Junit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).orElse(null); // Returns the value if present, otherwise returns null
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for updateEmployee method
    @DisplayName("Junit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("apple@gmail.com");
        employee.setFirstName("banana");

        // when - action or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("apple@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("banana");
    }

    // Junit test for deleteEmployee method
    @DisplayName("Junit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given - precondition or setup
        // willDoNothing() : the mock will not return anything
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when - action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        // verify() : Mockito verify() method can be used to test number of method invocations(=메서드 호출 수)
        // return 값이 없기 때문에 메서드 호출 수 확인
        verify(employeeRepository, times(1)).deleteById(employeeId); // 1번 호출되었는지 검증
    }
}
