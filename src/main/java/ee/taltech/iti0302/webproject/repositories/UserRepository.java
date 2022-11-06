package ee.taltech.iti0302.webproject.repositories;

import ee.taltech.iti0302.webproject.entities.AppUser;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface UserRepository extends JpaRepositoryImplementation<AppUser, Integer> {
}
