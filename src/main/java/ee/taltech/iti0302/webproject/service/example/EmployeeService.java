package ee.taltech.iti0302.webproject.service.example;

import ee.taltech.iti0302.webproject.dto.EmployeeDto;
import ee.taltech.iti0302.webproject.entity.Employee;
import ee.taltech.iti0302.webproject.repository.example.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public Optional<EmployeeDto> findById(Integer employeeId) {
        return employeeRepository.findById(employeeId).map(employeeMapper::toDto);
    }

    public void save() {
        // SEE ON NÄIDE
        Employee e = new Employee();
        e.setFirstName("Mingi Nimi");
        employeeRepository.save(e);
    }

    public List<EmployeeDto> findByFirstName(String firstName) {
        return employeeMapper.toDtoList(employeeRepository.findAllByFirstNameIgnoreCase(firstName));
    }
    public List<EmployeeDto> findByFirstNameContains(String firstName) {
        return employeeMapper.toDtoList(employeeRepository.findAllByFirstNameContainsIgnoreCase(firstName));
    }
}
