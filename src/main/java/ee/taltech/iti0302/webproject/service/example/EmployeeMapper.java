package ee.taltech.iti0302.webproject.service.example;

import ee.taltech.iti0302.webproject.dto.EmployeeDto;
import ee.taltech.iti0302.webproject.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    List<EmployeeDto> toDtoList(List<Employee> employees);
}
