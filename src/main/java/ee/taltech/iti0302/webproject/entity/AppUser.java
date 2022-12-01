package ee.taltech.iti0302.webproject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;

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
