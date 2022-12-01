package ee.taltech.iti0302.webproject.repository;

import ee.taltech.iti0302.webproject.entity.AppUser;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;


public interface UserRepository extends JpaRepositoryImplementation<AppUser, Integer> {

    Optional<AppUser> findByUsernameIgnoreCase(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(Integer id);
}
