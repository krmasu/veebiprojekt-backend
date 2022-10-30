package ee.taltech.iti0302.webproject.services.example;

import ee.taltech.iti0302.webproject.dto.EmployeeDto;
import ee.taltech.iti0302.webproject.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    @Mapping(source = "role.name", target = "roleName")
    EmployeeDto toDto(Employee employee);

    List<EmployeeDto> toDtoList(List<Employee> employees);
}
