package mypackage.hrms.business.abstracts;

import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.EmployerContacts;
import mypackage.hrms.entities.concretes.Employers;
import mypackage.hrms.entities.concretes.JobPostings;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployersService {
	DataNotification<List<Employers>> getAll();
	Notification add(Employers employer);
	Notification update(Employers employer);
	Notification delete(int id);
	Notification verifyKYCEmployers(int id, MultipartFile document);
	Notification verifyEmailEmployers(String email, String code);
	DataNotification<Employers> findByTaxNumber(String taxNumber);

	// Contact operations
	DataNotification<List<EmployerContacts>> getContactsByEmployerId(int employerId);
	Notification addContact(EmployerContacts contact);
	Notification updateContact(EmployerContacts contact);
	Notification deleteContact(long contactId);

	// Job posting operations
	DataNotification<List<JobPostings>> getJobPostingsByEmployerId(int employerId);
	Notification addJobPosting(JobPostings jobPosting);
	Notification updateJobPosting(JobPostings jobPosting);
	Notification deleteJobPosting(long jobPostingId);
}