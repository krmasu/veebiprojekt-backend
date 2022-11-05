package ee.taltech.iti0302.webproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany(mappedBy = "labels")
    private List<Task> tasks;
}
