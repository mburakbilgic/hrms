package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import mypackage.hrms.entities.concretes.JobPostings;

import java.util.List;

public interface JobPostingsDao extends JpaRepository<JobPostings, Long> {
    List<JobPostings> findByEmployerId(Integer employerId);
}