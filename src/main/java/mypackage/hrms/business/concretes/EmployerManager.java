package mypackage.hrms.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import mypackage.hrms.core.VerificationService.VerificateDocuments.VerificateDocuments;
import mypackage.hrms.core.VerificationService.VerificateKYC.VerificateKYC;
import mypackage.hrms.core.VerificationService.VerificateMail.MailVerificateService;
import org.springframework.stereotype.Service;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.core.utilities.notifications.*;
import mypackage.hrms.dataAccess.abstracts.EmployersDao;
import mypackage.hrms.entities.concretes.Employers;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployerManager implements EmployersService {

	private EmployersDao employersDao;
	private VerificateKYC kycVerification;
	private MailVerificateService mailVerificateService;

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
		return new DataNotification<>(employers, true, "Employers retrieved successfully.");
	}

	@Override
	public Notification add(Employers employer) {
		if (
				employer.getEmail() == null ||
				employer.getPassword() == null ||
				employersDao.findByEmail(employer.getEmail()).isPresent()
		) {
			return new Notification(false, "Email already exists or invalid data.");
		}

		employer.setActivateStatusEmail(false);
		employersDao.save(employer);

		String verificationCode = String.valueOf(new Random().nextInt(900000) + 100000);
		mailVerificateService.sendVerificationEmail(employer.getEmail(), verificationCode);

		return new Notification(true, "Employer added successfully.");

	}

	@Override
	public Notification update(Employers employer) {
		if (employersDao.existsById(employer.getId())) {
			employersDao.save(employer);
			return new Notification (true, "Employers updated successfully.");
		}
		return new Notification (false, "Invalid data or employer not found.");
	}

	@Override
	public Notification delete(int id) {
		Optional<Employers> employer = employersDao.findById(id);
		if (employer.isPresent()) {
			employersDao.deleteById(id);
			return new Notification (true, "Employers deleted successfully.");
		}
		return new Notification (false, "Employers not found.");
	}

	@Override
	public Notification verifyEmailEmployers(String email, String code) {
		Optional<Employers> employerOpt = employersDao.findByEmail(email);
		if (employerOpt.isEmpty()) {
			return new Notification(false,"Employer not found.");
		}

		Employers employer = employerOpt.get();
		boolean isVerified = mailVerificateService.verifyEmail(email, code);
		if (isVerified) {
			employer.setActivateStatusEmail(true);
			employersDao.saveAndFlush(employer);
			return new Notification(true,"Verification successfully!");
		} else {
			return new Notification(false,"Invalid verification code or email!");
		}
	}

	@Override
	public Notification verifyKYCEmployers(int id, MultipartFile document) {
		Optional<Employers> employerOpt = employersDao.findById(id);
		if (employerOpt.isEmpty()) {
			return new Notification(false,"Employer not found.");
		}

		Employers employer = employerOpt.get();
		boolean kycResult = kycVerification.verifyEmployers(employer);

		if (kycResult) {
			employer.setActivateStatusKYC(true);
			employersDao.saveAndFlush(employer);
			return new Notification(true,"KYC verification successfully.");
		} else {
			return new Notification(false, "Invalid request or missing file.");
		}
	}

}