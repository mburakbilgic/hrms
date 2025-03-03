package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import mypackage.hrms.entities.concretes.Users;
import java.util.Optional;

public interface UsersDao extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
}
