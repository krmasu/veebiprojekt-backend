package ee.taltech.iti0302.webproject.repositories.example;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter
@Entity
public class Employee {
    @Id
    private Integer id;
    private String firstName;
    private String idCode;
    private Integer roleId;

}
