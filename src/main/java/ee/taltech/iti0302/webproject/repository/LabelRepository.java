package ee.taltech.iti0302.webproject.repository;

import ee.taltech.iti0302.webproject.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface LabelRepository extends JpaRepositoryImplementation<Label, Integer> {
    Page<Label> findAllByProjectId(Integer projectId, Pageable pageable);
}
