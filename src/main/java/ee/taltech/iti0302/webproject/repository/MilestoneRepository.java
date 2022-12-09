package ee.taltech.iti0302.webproject.repository;

import ee.taltech.iti0302.webproject.entity.Milestone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface MilestoneRepository extends JpaRepositoryImplementation<Milestone, Integer> {
    Page<Milestone> findAllByProjectId(Integer projectid, Pageable pageable);
}
