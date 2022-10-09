package ee.taltech.iti0302.webproject.controllers.example;

import ee.taltech.iti0302.webproject.repositories.example.Employee;
import ee.taltech.iti0302.webproject.services.example.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TestController {
    private final EmployeeService employeeService;

    public TestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String hello() {
        return "Hello World";
    }
    @GetMapping("test")
    public String test() {
        return "test";
    }

    // http://localhost:8080/employee/1
    @GetMapping("employee/{employeeId}")
    public Optional<Employee> getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.findById(employeeId);
    }

    // http://localhost:8080/employee?firstName=mingiNimiAndmebaasist
    @GetMapping("employee")
    public List<Employee> getEmployeeByFirstName(@RequestParam("firstName") String firstName) {
        return employeeService.findByFirstName(firstName);
    }

    @GetMapping("employee2")
    public List<Employee> getEmployeeByFirstNameContains(@RequestParam("firstNameContains") String firstNameContains) {
        return employeeService.findByFirstNameContains(firstNameContains);
    }

    public void save() {
        employeeService.save();
    }
}
