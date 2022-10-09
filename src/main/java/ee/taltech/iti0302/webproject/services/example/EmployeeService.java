package ee.taltech.iti0302.webproject.services.example;

import ee.taltech.iti0302.webproject.repositories.example.Employee;
import ee.taltech.iti0302.webproject.repositories.example.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> findById(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void save() {
        // SEE ON NÄIDE
        Employee e = new Employee();
        e.setFirstName("Mingi Nimi2");
        employeeRepository.save(e);
    }

    public List<Employee> findByFirstName(String firstName) {
        return employeeRepository.findAllByFirstNameIgnoreCase(firstName);
    }
    public List<Employee> findByFirstNameContains(String firstName) {
        return employeeRepository.findAllByFirstNameContainsIgnoreCase(firstName);
    }
}
