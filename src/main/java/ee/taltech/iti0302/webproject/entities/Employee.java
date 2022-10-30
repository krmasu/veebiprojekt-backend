package ee.taltech.iti0302.webproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String idCode;
    private Integer roleId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
