package ee.taltech.iti0302.webproject.repository;

import ee.taltech.iti0302.webproject.entity.Project;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ProjectRepository extends JpaRepositoryImplementation<Project, Integer> {
}
