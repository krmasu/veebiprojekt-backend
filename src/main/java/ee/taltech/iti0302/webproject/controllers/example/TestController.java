package ee.taltech.iti0302.webproject.controllers.example;

import ee.taltech.iti0302.webproject.dto.EmployeeDto;
import ee.taltech.iti0302.webproject.services.example.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final EmployeeService employeeService;

    @GetMapping
    public String hello() {
        return "Hello World";
    }
    @GetMapping("api/test")
    public String test() {
        return "test";
    }

    // http://localhost:8080/api/employee/1
    @GetMapping("api/employee/{employeeId}")
    public Optional<EmployeeDto> getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.findById(employeeId);
    }

    // http://localhost:8080/employee?firstName=mingiNimiAndmebaasist
    @GetMapping("api/employee")
    public List<EmployeeDto> getEmployeeByFirstName(@RequestParam("firstName") String firstName) {
        return employeeService.findByFirstName(firstName);
    }

    @GetMapping("api/employee2")
    public List<EmployeeDto> getEmployeeByFirstNameContains(@RequestParam("firstNameContains") String firstNameContains) {
        return employeeService.findByFirstNameContains(firstNameContains);
    }

    public void save() {
        employeeService.save();
    }
}
