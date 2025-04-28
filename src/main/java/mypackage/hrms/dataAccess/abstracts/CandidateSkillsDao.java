package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import mypackage.hrms.entities.concretes.CandidateSkills;

import java.util.List;

public interface CandidateSkillsDao extends JpaRepository<CandidateSkills, Long> {
    List<CandidateSkills> findByCandidateId(Integer candidateId);
}