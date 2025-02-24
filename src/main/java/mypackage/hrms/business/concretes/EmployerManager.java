package mypackage.hrms.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.core.VerificationService.VerificateMail.MailVerificateService;
import mypackage.hrms.core.VerificationService.VerificatePerson.VerificatePerson;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.dataAccess.abstracts.EmployersDao;
import mypackage.hrms.entities.concretes.Employers;

@Service
public class EmployerManager implements EmployersService {

	private EmployersDao employersDao;
	private VerificatePerson kycVerification;
	private MailVerificateService mailVerificateService;

	public EmployerManager(EmployersDao employersDao,
						   VerificatePerson kycVerification,
						   MailVerificateService mailVerificateService) {
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
		if (employer.getEmail() == null || employer.getPassword() == null) {
			return new Notification(false, "Email and password are required!");
		}

		employer.setActivateStatus(false);
		employersDao.save(employer);

		return new Notification(true, "Employer added successfully.");

	}

	@Override
	public Notification update(Employers employers) {
		if (employersDao.existsById(employers.getId())) {
			employersDao.save(employers);
			return new Notification (true, "Employers updated successfully.");
		}
		return new Notification (false, "Employers not found.");
	}

	@Override
	public Notification delete(int id) {
		Optional<Employers> employers = employersDao.findById(id);
		if (employers.isPresent()) {
			employersDao.deleteById(id);
			return new Notification (true, "Employers deleted successfully.");
		}
		return new Notification (false, "Employers not found.");
	}

	@Override
	public Notification  verifyEmployers(int id) {
		Optional<Employers> employersOpt = employersDao.findById(id);
		if (employersOpt.isEmpty()) {
			return new Notification (false, "Employers not found.");
		}

		Employers employers = employersOpt.get();
		boolean kycResult = kycVerification.verifyEmployers(employers);
		// boolean mailResult = mailVerificateService.sendVerificationEmail(employers.getEmail());
		boolean mailResult = true;

		if (kycResult && mailResult) {
			return new Notification (true, "Employers verified successfully.");
		} else if (!kycResult) {
			return new Notification (false, "KYC verification failed.");
		} else {
			return new Notification (false, "Email verification failed.");
		}
	}

}