package ee.taltech.iti0302.webproject.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "milestone")
    private List<Task> tasks;

}
