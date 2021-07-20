package mypackage.hrms.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import mypackage.hrms.business.abstracts.CandidatesService;
import mypackage.hrms.dataAccess.abstracts.CandidatesDao;
import mypackage.hrms.entities.concretes.Candidates;

@Service
public class CandidatesManager implements CandidatesService {

	private CandidatesDao candidatesDao;

	public CandidatesManager(CandidatesDao candidatesDao) {
		super();
		this.candidatesDao = candidatesDao;
	}

	@Override
	public List<Candidates> getAll() {
		return this.candidatesDao.findAll();
	}

}
