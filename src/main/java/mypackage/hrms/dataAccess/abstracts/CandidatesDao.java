package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import mypackage.hrms.entities.concretes.Candidates;

import java.util.Optional;

public interface CandidatesDao extends JpaRepository<Candidates, Integer> {
    Optional<Candidates> findByEmail(String email);
}