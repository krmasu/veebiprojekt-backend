package ee.taltech.iti0302.webproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Getter @Setter
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    @OneToMany(mappedBy = "project")
    private List<Milestone> milestones;

    @OneToMany(mappedBy = "project")
    private List<Label> labels;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @ManyToMany(mappedBy = "projects")
    private List<AppUser> users;
}
