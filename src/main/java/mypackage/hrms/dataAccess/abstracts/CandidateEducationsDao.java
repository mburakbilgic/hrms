package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import mypackage.hrms.entities.concretes.CandidateEducations;

import java.util.List;

public interface CandidateEducationsDao extends JpaRepository<CandidateEducations, Long> {
    List<CandidateEducations> findByCandidateId(Integer candidateId);
}