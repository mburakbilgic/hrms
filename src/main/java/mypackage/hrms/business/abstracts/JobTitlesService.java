package mypackage.hrms.business.abstracts;

import java.util.List;

import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.JobTitles;

public interface JobTitlesService {
	DataNotification<List<JobTitles>> getAll();
	Notification update(JobTitles jobTitles);
	Notification delete(int id);

}