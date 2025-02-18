package mypackage.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mypackage.hrms.business.abstracts.JobTitlesService;
import mypackage.hrms.dataAccess.abstracts.JobTitlesDao;
import mypackage.hrms.entities.concretes.JobTitles;

@Service
public class JobTitlesManager implements JobTitlesService{
	private JobTitlesDao jobTitlesDao;

	@Autowired
	public JobTitlesManager(JobTitlesDao jobTitlesDao) {
		super();
		this.jobTitlesDao = jobTitlesDao;
	}

	@Override
	public List<JobTitles> getAll() {
		return this.jobTitlesDao.findAll();
	}

}