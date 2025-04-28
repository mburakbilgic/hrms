package mypackage.hrms.business.concretes;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.core.VerificationService.VerificateKYC.VerificateKYC;
import mypackage.hrms.core.VerificationService.VerificateMail.MailVerificateService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.dataAccess.abstracts.EmployerContactsDao;
import mypackage.hrms.dataAccess.abstracts.EmployersDao;
import mypackage.hrms.dataAccess.abstracts.JobPostingsDao;
import mypackage.hrms.entities.concretes.EmployerContacts;
import mypackage.hrms.entities.concretes.Employers;
import mypackage.hrms.entities.concretes.JobPostings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployerManager implements EmployersService {

	private final EmployersDao employersDao;
	private final EmployerContactsDao contactsDao;
	private final JobPostingsDao jobPostingsDao;
	private final VerificateKYC kycVerification;
	private final MailVerificateService mailVerificateService;

	public EmployerManager(EmployersDao employersDao,
						   EmployerContactsDao contactsDao,
						   JobPostingsDao jobPostingsDao,
						   VerificateKYC kycVerification,
						   MailVerificateService mailVerificateService) {
		this.employersDao = employersDao;
		this.contactsDao = contactsDao;
		this.jobPostingsDao = jobPostingsDao;
		this.kycVerification = kycVerification;
		this.mailVerificateService = mailVerificateService;
	}

	@Override
	public DataNotification<List<Employers>> getAll() {
		List<Employers> employers = employersDao.findAll();
		String message = employers.isEmpty() ? "No employers found." : "Employers retrieved successfully.";
		return new DataNotification<>(employers, true, message);
	}

	@Override
	public Notification add(Employers employer) {
		if (!isValidEmployer(employer)) {
			return createErrorNotification("Invalid data or email/tax number already exists.");
		}

		employer.setActivateStatusEmail(false);
		employer.setActivateStatusKyc(false);
		employer.setActivateStatusSms(false);
		employer.setStatus("Pending");

		try {
			employersDao.save(employer);
			String verificationCode = String.valueOf(new Random().nextInt(900000) + 100000);
			mailVerificateService.sendVerificationEmail(employer.getEmail(), verificationCode);
			return createSuccessNotification("Employer added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add employer: " + e.getMessage());
		}
	}

	@Override
	public Notification update(Employers employer) {
		if (!employersDao.existsById(employer.getId()) || !isValidEmployer(employer)) {
			return createErrorNotification("Invalid data or employer not found.");
		}

		try {
			employersDao.save(employer);
			return createSuccessNotification("Employer updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update employer: " + e.getMessage());
		}
	}

	@Override
	public Notification delete(int id) {
		if (!employersDao.existsById(id)) {
			return createErrorNotification("Employer not found.");
		}

		try {
			employersDao.deleteById(id);
			return createSuccessNotification("Employer deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete employer: " + e.getMessage());
		}
	}

	@Override
	public Notification verifyEmailEmployers(String email, String code) {
		Optional<Employers> employerOpt = employersDao.findByEmail(email);
		if (employerOpt.isEmpty()) {
			return createErrorNotification("Employer not found.");
		}

		Employers employer = employerOpt.get();
		boolean isVerified = mailVerificateService.verifyEmail(email, code);
		if (isVerified) {
			employer.setActivateStatusEmail(true);
			updateEmployerStatus(employer);
			employersDao.save(employer);
			return createSuccessNotification("Email verified successfully.");
		}
		return createErrorNotification("Invalid verification code or email.");
	}

	@Override
	public Notification verifyKYCEmployers(int id, MultipartFile document) {
		Optional<Employers> employerOpt = employersDao.findById(id);
		if (employerOpt.isEmpty()) {
			return createErrorNotification("Employer not found.");
		}

		Employers employer = employerOpt.get();
		boolean kycResult = kycVerification.verifyEmployers(employer);
		if (kycResult) {
			employer.setActivateStatusKyc(true);
			updateEmployerStatus(employer);
			employersDao.save(employer);
			return createSuccessNotification("KYC verified successfully.");
		}
		return createErrorNotification("Invalid request or missing file.");
	}

	@Override
	public DataNotification<Employers> findByTaxNumber(String taxNumber) {
		Optional<Employers> employerOpt = employersDao.findByTaxNumber(taxNumber);
		if (employerOpt.isEmpty()) {
			return new DataNotification<>(null, false, "Employer not found.");
		}
		return new DataNotification<>(employerOpt.get(), true, "Employer retrieved successfully.");
	}

	@Override
	public DataNotification<List<EmployerContacts>> getContactsByEmployerId(int employerId) {
		if (!employersDao.existsById(employerId)) {
			return new DataNotification<>(null, false, "Employer not found.");
		}
		List<EmployerContacts> contacts = contactsDao.findByEmployerId(employerId);
		String message = contacts.isEmpty() ? "No contacts found." : "Contacts retrieved successfully.";
		return new DataNotification<>(contacts, true, message);
	}

	@Override
	public Notification addContact(EmployerContacts contact) {
		if (!isValidContact(contact)) {
			return createErrorNotification("Invalid contact data.");
		}

		try {
			contactsDao.save(contact);
			return createSuccessNotification("Contact added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add contact: " + e.getMessage());
		}
	}

	@Override
	public Notification updateContact(EmployerContacts contact) {
		if (!contactsDao.existsById(contact.getId()) || !isValidContact(contact)) {
			return createErrorNotification("Invalid contact data or contact not found.");
		}

		try {
			contactsDao.save(contact);
			return createSuccessNotification("Contact updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update contact: " + e.getMessage());
		}
	}

	@Override
	public Notification deleteContact(long contactId) {
		if (!contactsDao.existsById(contactId)) {
			return createErrorNotification("Contact not found.");
		}

		try {
			contactsDao.deleteById(contactId);
			return createSuccessNotification("Contact deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete contact: " + e.getMessage());
		}
	}


	@Override
	public DataNotification<List<JobPostings>> getJobPostingsByEmployerId(int employerId) {
		if (!employersDao.existsById(employerId)) {
			return new DataNotification<>(null, false, "Employer not found.");
		}
		List<JobPostings> jobPostings = jobPostingsDao.findByEmployerId(employerId);
		String message = jobPostings.isEmpty() ? "No job postings found." : "Job postings retrieved successfully.";
		return new DataNotification<>(jobPostings, true, message);
	}

	@Override
	public Notification addJobPosting(JobPostings jobPosting) {
		if (!isValidJobPosting(jobPosting)) {
			return createErrorNotification("Invalid job posting data.");
		}

		try {
			jobPostingsDao.save(jobPosting);
			return createSuccessNotification("Job posting added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add job posting: " + e.getMessage());
		}
	}

	@Override
	public Notification updateJobPosting(JobPostings jobPosting) {
		if (!jobPostingsDao.existsById(jobPosting.getId()) || !isValidJobPosting(jobPosting)) {
			return createErrorNotification("Invalid job posting data or job posting not found.");
		}

		try {
			jobPostingsDao.save(jobPosting);
			return createSuccessNotification("Job posting updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update job posting: " + e.getMessage());
		}
	}

	@Override
	public Notification deleteJobPosting(long jobPostingId) {
		if (!jobPostingsDao.existsById(jobPostingId)) {
			return createErrorNotification("Job posting not found.");
		}

		try {
			jobPostingsDao.deleteById(jobPostingId);
			return createSuccessNotification("Job posting deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete job posting: " + e.getMessage());
		}
	}

	private boolean isValidEmployer(Employers employer) {
		if (employer == null ||
				employer.getEmail() == null ||
				employer.getPassword() == null ||
				employer.getCompanyName() == null ||
				employer.getWebAddress() == null ||
				employer.getTaxNumber() == null) {
			return false;
		}
		if (!employer.getWebAddress().matches("^https?://[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			return false;
		}
		Optional<Employers> existingEmployerByEmail = employersDao.findByEmail(employer.getEmail());
		Optional<Employers> existingEmployerByTaxNumber = employersDao.findByTaxNumber(employer.getTaxNumber());
		return !(existingEmployerByEmail.isPresent() && (employer.getId() == null || !existingEmployerByEmail.get().getId().equals(employer.getId()))) &&
				!(existingEmployerByTaxNumber.isPresent() && (employer.getId() == null || !existingEmployerByTaxNumber.get().getId().equals(employer.getId())));
	}

	private boolean isValidContact(EmployerContacts contact) {
		return contact != null &&
				contact.getEmployer() != null &&
				employersDao.existsById(contact.getEmployer().getId()) &&
				contact.getContactType() != null &&
				contact.getContactType().matches("PHONE|EMAIL|ADDRESS|OTHER") &&
				contact.getContactValue() != null &&
				!contact.getContactValue().isBlank();
	}

	private boolean isValidJobPosting(JobPostings jobPosting) {
		return jobPosting != null &&
				jobPosting.getEmployer() != null &&
				employersDao.existsById(jobPosting.getEmployer().getId()) &&
				jobPosting.getJobTitle() != null &&
				jobPosting.getDescription() != null &&
				!jobPosting.getDescription().isBlank() &&
				jobPosting.getStatus() != null &&
				jobPosting.getStatus().matches("Open|Closed");
	}

	private void updateEmployerStatus(Employers employer) {
		if (employer.isActivateStatusEmail() &&
				employer.isActivateStatusKyc() &&
				employer.isActivateStatusSms()) {
			employer.setStatus("Active");
		} else {
			employer.setStatus("Pending");
		}
	}

	private Notification createSuccessNotification(String message) {
		return new Notification(true, message);
	}

	private Notification createErrorNotification(String message) {
		return new Notification(false, message);
	}
}