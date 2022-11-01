package net.javaguides.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
}
