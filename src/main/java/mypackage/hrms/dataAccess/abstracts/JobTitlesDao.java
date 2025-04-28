package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import mypackage.hrms.entities.concretes.JobTitles;

import java.util.Optional;

public interface JobTitlesDao extends JpaRepository<JobTitles, Long> {
    Optional<JobTitles> findByTitle(String title);
}