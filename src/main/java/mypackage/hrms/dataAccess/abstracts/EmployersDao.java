package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import mypackage.hrms.entities.concretes.Employers;

import java.util.Optional;

public interface EmployersDao extends JpaRepository<Employers, Integer> {
    Optional<Employers> findById(Integer id);
    Optional<Employers> findByEmail(String email);
}