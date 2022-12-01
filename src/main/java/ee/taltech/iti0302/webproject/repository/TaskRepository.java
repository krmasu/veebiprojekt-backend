package ee.taltech.iti0302.webproject.repository;

import ee.taltech.iti0302.webproject.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface TaskRepository extends JpaRepositoryImplementation<Task, Integer> {

    Page<Task> findAllByProjectId(Integer projectId, Pageable pageable);

}
