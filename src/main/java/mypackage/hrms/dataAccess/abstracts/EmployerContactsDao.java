package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import mypackage.hrms.entities.concretes.EmployerContacts;

import java.util.List;

public interface EmployerContactsDao extends JpaRepository<EmployerContacts, Long> {
    List<EmployerContacts> findByEmployerId(Integer employerId);
}