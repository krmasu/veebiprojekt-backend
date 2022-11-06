package ee.taltech.iti0302.webproject.repositories;

import ee.taltech.iti0302.webproject.entities.Project;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ProjectRepository extends JpaRepositoryImplementation<Project, Integer> {

}
