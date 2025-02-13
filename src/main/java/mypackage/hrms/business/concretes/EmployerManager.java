package mypackage.hrms.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.dataAccess.abstracts.EmployersDao;
import mypackage.hrms.entities.concretes.Employers;

@Service
public class EmployerManager implements EmployersService {

	private EmployersDao employersDao;

	public EmployerManager(EmployersDao employersDao) {
		super();
		this.employersDao = employersDao;

	}

	@Override
	public List<Employers> getAll() {
		return this.employersDao.findAll();
	}

}
