package mypackage.hrms.business.concretes;

import mypackage.hrms.business.abstracts.JobTitlesService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.dataAccess.abstracts.JobTitlesDao;
import mypackage.hrms.entities.concretes.JobTitles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobTitlesManager implements JobTitlesService {
	private final JobTitlesDao jobTitlesDao;

	@Autowired
	public JobTitlesManager(JobTitlesDao jobTitlesDao) {
		this.jobTitlesDao = jobTitlesDao;
	}

	@Override
	public DataNotification<List<JobTitles>> getAll() {
		List<JobTitles> jobTitles = jobTitlesDao.findAll();
		String message = jobTitles.isEmpty() ? "No job titles found." : "Jobs retrieved successfully.";
		return new DataNotification<>(jobTitles, true, message);
	}

	@Override
	public Notification add(JobTitles jobTitles) {
		if (!isValidJobTitle(jobTitles)) {
			return createErrorNotification("Job title already exists or invalid data.");
		}

		return saveJobTitle(jobTitles);
	}

	@Override
	public Notification update(JobTitles jobTitles) {
		if (!jobTitlesDao.existsById(jobTitles.getId())) {
			return createErrorNotification("Job title not found.");
		}

		return saveJobTitle(jobTitles);
	}

	@Override
	public Notification delete(int id) {
		if (!jobTitlesDao.existsById(id)) {
			return createErrorNotification("Job title not found.");
		}

		jobTitlesDao.deleteById(id);
		return createSuccessNotification("Job title deleted successfully.");
	}

	private Notification saveJobTitle(JobTitles jobTitles) {
		try {
			jobTitlesDao.save(jobTitles);
			return createSuccessNotification("Job title " + (jobTitles.getId() == 0 ? "added" : "updated") + " successfully.");
		} catch (Exception e) {
			return createErrorNotification("Job title already exists or invalid data.");
		}
	}

	private boolean isValidJobTitle(JobTitles jobTitles) {
		if (jobTitles == null || jobTitles.getTitle() == null) {
			return false;
		}
		return jobTitlesDao.findByTitle(jobTitles.getTitle()).isEmpty();
	}

	private Notification createSuccessNotification(String message) {
		return new Notification(true, message);
	}

	private Notification createErrorNotification(String message) {
		return new Notification(false, message);
	}
}