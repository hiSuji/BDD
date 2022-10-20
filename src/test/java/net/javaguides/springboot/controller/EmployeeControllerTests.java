package net.javaguides.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// @WebMvcTest annotation will load only the spring beans that are required to test the controller.
// It won't load the service layer components or the repository component.
@WebMvcTest
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc; // MockMvc to call REST API.

    // @MockBean annotation will tell spring that create this mock object.
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper Jackson class to serialize and decentralize java objects.

    // Junit test for
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Banana")
                .lastName("Kim")
                .email("banana@gmail.com")
                .build();

        // ArgumentMatchers : Allows creating customized argument matchers.
        // any() → Matches anything (null values)
        // willAnswer : 매개 변수 사용 반환 값 결정
        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0)); // in this case we only have one parameter, it is on place 0

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees") // post REST API 호출
                .contentType(MediaType.APPLICATION_JSON) // Json type
                .content(objectMapper.writeValueAsString(employee))); // 본문에 employee JSON 객체 전달 (writeValueAsString : Java 객체에서 JSON 생성하고 생성된 JSON -> 문자열로 반환)

        // then - verify the output
        // jsonPath - ($ : The root element to query. This starts all path expressions.)
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    // Junit test for Get All employees REST API
    @Test
    public void givenListOfEmployeeObject_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Park").lastName("JiYun").email("parkJiyun@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Kim").lastName("EnJu").email("kimEnjun@gmail.com").build());
        // willReturn : 고정 값 반환
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then - verify the output
        // andExpect() : 응답 검증
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfEmployees.size())));
    }
}