package ee.taltech.iti0302.webproject.repository;

import ee.taltech.iti0302.webproject.entity.Task;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface TaskRepository extends JpaRepositoryImplementation<Task, Integer> {
}
