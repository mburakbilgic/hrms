package mypackage.hrms.business.concretes;

import mypackage.hrms.business.abstracts.JobTitlesService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.dataAccess.abstracts.JobTitlesDao;
import mypackage.hrms.entities.concretes.JobTitles;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobTitlesManager implements JobTitlesService {

	private final JobTitlesDao jobTitlesDao;

	public JobTitlesManager(JobTitlesDao jobTitlesDao) {
		this.jobTitlesDao = jobTitlesDao;
	}

	@Override
	public DataNotification<List<JobTitles>> getAll() {
		List<JobTitles> jobTitles = jobTitlesDao.findAll();
		String message = jobTitles.isEmpty() ? "No job titles found." : "Job titles retrieved successfully.";
		return new DataNotification<>(jobTitles, true, message);
	}

	@Override
	public Notification add(JobTitles jobTitle) {
		if (!isValidJobTitle(jobTitle)) {
			return createErrorNotification("Invalid data or title already exists.");
		}

		try {
			jobTitlesDao.save(jobTitle);
			return createSuccessNotification("Job title added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add job title: " + e.getMessage());
		}
	}

	@Override
	public Notification update(JobTitles jobTitle) {
		if (!jobTitlesDao.existsById(jobTitle.getId()) || !isValidJobTitle(jobTitle)) {
			return createErrorNotification("Invalid data or job title not found.");
		}

		try {
			jobTitlesDao.save(jobTitle);
			return createSuccessNotification("Job title updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update job title: " + e.getMessage());
		}
	}

	@Override
	public Notification delete(Long id) {
		if (!jobTitlesDao.existsById(id)) {
			return createErrorNotification("Job title not found.");
		}

		try {
			jobTitlesDao.deleteById(id);
			return createSuccessNotification("Job title deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete job title: " + e.getMessage());
		}
	}

	private boolean isValidJobTitle(JobTitles jobTitle) {
		if (jobTitle == null || jobTitle.getTitle() == null || jobTitle.getTitle().isBlank()) {
			return false;
		}
		Optional<JobTitles> existingJobTitle = jobTitlesDao.findByTitle(jobTitle.getTitle());
		return !(existingJobTitle.isPresent() && (jobTitle.getId() == null || !existingJobTitle.get().getId().equals(jobTitle.getId())));
	}

	private Notification createSuccessNotification(String message) {
		return new Notification(true, message);
	}

	private Notification createErrorNotification(String message) {
		return new Notification(false, message);
	}
}