package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import mypackage.hrms.entities.concretes.CandidateExperiences;

import java.util.List;

public interface CandidateExperiencesDao extends JpaRepository<CandidateExperiences, Long> {
    List<CandidateExperiences> findByCandidateId(Integer candidateId);
}