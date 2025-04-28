package mypackage.hrms.business.abstracts;

import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.JobTitles;

import java.util.List;

public interface JobTitlesService {
	DataNotification<List<JobTitles>> getAll();
	Notification add(JobTitles jobTitle);
	Notification update(JobTitles jobTitle);
	Notification delete(Long id);
}