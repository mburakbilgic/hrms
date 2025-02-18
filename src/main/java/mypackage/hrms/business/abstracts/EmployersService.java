package mypackage.hrms.business.abstracts;

import java.util.List;

import mypackage.hrms.entities.concretes.Employers;

public interface EmployersService {
	List<Employers> getAll();

}