package mypackage.hrms.business.concretes;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.core.VerificationService.VerificateKYC.VerificateKYC;
import mypackage.hrms.core.VerificationService.VerificateMail.MailVerificateService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.dataAccess.abstracts.EmployersDao;
import mypackage.hrms.entities.concretes.Employers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployerManager implements EmployersService {

	private final EmployersDao employersDao;
	private final VerificateKYC kycVerification;
	private final MailVerificateService mailVerificateService;

	public EmployerManager(EmployersDao employersDao,
						   MailVerificateService mailVerificateService,
						   VerificateKYC kycVerification) {
		this.employersDao = employersDao;
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

	private boolean isValidEmployer(Employers employer) {
		if (employer == null ||
				employer.getEmail() == null ||
				employer.getPassword() == null ||
				employer.getCompanyName() == null ||
				employer.getWebAddress() == null ||
				employer.getPhoneNumber() == null ||
				employer.getTaxNumber() == null) {
			return false;
		}
		if (!employer.getWebAddress().matches("^https?://[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			return false;
		}
		return !employersDao.findByEmail(employer.getEmail()).isPresent() &&
				!employersDao.existsByTaxNumber(employer.getTaxNumber());
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