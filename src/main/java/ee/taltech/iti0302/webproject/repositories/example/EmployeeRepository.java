package ee.taltech.iti0302.webproject.repositories.example;

import ee.taltech.iti0302.webproject.entities.Employee;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface EmployeeRepository extends JpaRepositoryImplementation<Employee, Integer> {

    List<Employee> findAllByFirstNameIgnoreCase(String firstName);
    List<Employee> findAllByFirstNameContainsIgnoreCase(String firstName);

}
