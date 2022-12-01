package ee.taltech.iti0302.webproject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "status")
    private List<Task> tasks;
}
