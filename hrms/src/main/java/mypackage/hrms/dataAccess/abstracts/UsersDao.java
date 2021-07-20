package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import mypackage.hrms.entities.concretes.JobTitles;

public interface UsersDao extends JpaRepository<JobTitles, Integer> {

}
