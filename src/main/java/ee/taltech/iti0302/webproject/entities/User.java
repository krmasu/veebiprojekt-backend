package ee.taltech.iti0302.webproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String email;

    @OneToMany(mappedBy = "assignee")
    private List<Task> assignedTasks;

    @ManyToMany
    @JoinTable(
            name = "user_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects;
}
