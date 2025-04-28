package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import mypackage.hrms.entities.concretes.CandidateLanguages;

import java.util.List;

public interface CandidateLanguagesDao extends JpaRepository<CandidateLanguages, Long> {
    List<CandidateLanguages> findByCandidateId(Integer candidateId);
}