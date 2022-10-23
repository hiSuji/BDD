package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController annotation to make this class as a spring MVC controller.
@RestController
// @RequestMapping annotation to define URL for all the REST APIs in this controller.
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // spring 4.3 버전 이후 spring IOC finds a spring bean with a single constructor
    // spring IOC will automatically inject this dependency.
    // @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // @RequestBody annotation internally use as HTTP message converters to convert JSON
    @PostMapping
    // REST API returns by default the status code as 200
    // @ResponseStatus 200이 아닌 새로운 응답 상태를 생성하고 싶을 때 사용
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() { return employeeService.getAllEmployees(); }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok) // Optional 객체의 값이 있다면, map() 함수를 통해 값의 형태를 변경한다.
                .orElseGet(() -> ResponseEntity.notFound().build()); // Optional 객체의 값이 없다면, 인수로 전달된 공급자 함수(Supplier)의 결과 값을 반환한다.
    }
}
