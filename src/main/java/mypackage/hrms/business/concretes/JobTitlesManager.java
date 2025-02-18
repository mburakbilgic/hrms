package mypackage.hrms.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mypackage.hrms.business.abstracts.JobTitlesService;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.core.utilities.notifications.DataNotification;
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
	public DataNotification<List<JobTitles>> getAll() {
		List<JobTitles> jobTitles = jobTitlesDao.findAll();
		return new DataNotification<> (jobTitles, true, "Jobs retrieved successfully.");
	}

	@Override
	public Notification add(JobTitles jobTitles) {
		jobTitlesDao.save(jobTitles);
		return new Notification(true);
	}

	@Override
	public Notification update(JobTitles jobTitles) {
		if (jobTitlesDao.existsById(jobTitles.getId())) {
			jobTitlesDao.save(jobTitles);
			return new Notification (true, "Jobs updated successfully.");
		}
		return new Notification (false, "Jobs not found.");
	}

	@Override
	public Notification delete(int id) {
		Optional<JobTitles> jobTitles = jobTitlesDao.findById(id);
		if (jobTitles.isPresent()) {
			jobTitlesDao.deleteById(id);
			return new Notification (true, "Jobs deleted successfully.");
		}
		return new Notification (false, "Jobs not found.");
	}

}