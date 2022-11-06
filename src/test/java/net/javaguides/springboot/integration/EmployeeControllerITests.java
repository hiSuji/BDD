package net.javaguides.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
Integration tests separated from the unit tests.
Should not run along with the unit tests.
In case of integration testing
- Don't have to mock the dependent dependencies.
- Be not going to use Mockito.

@SpringBootTest annotation creates an application context and loads full application context.
this will load all the beans from all the layers into the application.

*/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {
    @Autowired
    private MockMvc mockMvc; // Injecting MockMvc class to make HTTP request using perform() method
    @Autowired
    private EmployeeRepository employeeRepository; // Injecting EmployeeRepository to use its method's to perform different operations on database
    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper for serialization and deserialization

    @BeforeEach
    void setup() { // clean for each and every test case
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Banana")
                .lastName("Kim")
                .email("banana@gmail.com")
                .build();

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees") // post REST API 호출
                .contentType(MediaType.APPLICATION_JSON) // Json type
                .content(objectMapper.writeValueAsString(employee))); // 본문에 employee JSON 객체 전달 (writeValueAsString : Java 객체에서 JSON 생성하고 생성된 JSON -> 문자열로 반환)

        // then - verify the output
        // jsonPath - ($ : The root element to query. This starts all path expressions.)
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @Test
    public void givenListOfEmployeeObject_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Park").lastName("JiYun").email("parkJiyun@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Kim").lastName("EnJu").email("kimEnjun@gmail.com").build());
        employeeRepository.saveAll(listOfEmployees); // 데이터베이스에 레코드 저장

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then - verify the output
        // andExpect() : 응답 검증
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    @Test
    public void givenEmployeeById_whenGetEmployeeById_thenReturnsEmployee() throws Exception{
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Banana")
                .lastName("Kim")
                .email("banana@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));


        // then - verify the output
        // $ : root member of a JSON structure whether it is an object or array.
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    public void givenInvalidEmployeeById_whenGetEmployeeById_thenReturnsEmpty() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Banana")
                .lastName("Kim")
                .email("banana@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        // $ : root member of a JSON structure whether it is an object or array.
        response.andDo(print())
                .andExpect(status().isNotFound());

    }
}
