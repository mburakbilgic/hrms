package mypackage.hrms.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import mypackage.hrms.core.VerificationService.VerificateMail.MailVerificateService;
import org.springframework.stereotype.Service;

import mypackage.hrms.business.abstracts.CandidatesService;
import mypackage.hrms.core.VerificationService.VerificateKYC.VerificateKYC;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.dataAccess.abstracts.CandidatesDao;
import mypackage.hrms.entities.concretes.Candidates;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CandidatesManager implements CandidatesService {

	private CandidatesDao candidatesDao;
	private VerificateKYC kycVerification;
	private MailVerificateService mailVerificateService;

	public CandidatesManager(CandidatesDao candidatesDao,
							 MailVerificateService mailVerificateService,
							 VerificateKYC kycVerification) {
		this.candidatesDao = candidatesDao;
		this.kycVerification = kycVerification;
		this.mailVerificateService = mailVerificateService;
	}

	@Override
	public DataNotification<List<Candidates>> getAll() {
		List<Candidates> candidates = candidatesDao.findAll();
		return new DataNotification<>(candidates, true, "Candidates retrieved successfully.");
	}

	@Override
	public Notification add(Candidates candidate) {
		if (
				candidate.getEmail() == null ||
				candidate.getPassword() == null ||
				candidatesDao.findByEmail(candidate.getEmail()).isPresent()
		) {
			return new Notification(false, "Email already exists or invalid data.");
		}

		candidate.setActivateStatusEmail(false);
		candidatesDao.save(candidate);

		String verificationCode = String.valueOf(new Random().nextInt(900000) + 100000);
		mailVerificateService.sendVerificationEmail(candidate.getEmail(), verificationCode);

		return new Notification(true, "Candidate added successfully.");

	}

	@Override
	public Notification update(Candidates candidate) {
		if (candidatesDao.existsById(candidate.getId())) {
			candidatesDao.save(candidate);
			return new Notification (true, "Candidate updated successfully.");
		}
		return new Notification (false, "Invalid data or candidate not found.");
	}

	@Override
	public Notification delete(int id) {
		Optional<Candidates> candidate = candidatesDao.findById(id);
		if (candidate.isPresent()) {
			candidatesDao.deleteById(id);
			return new Notification (true, "Candidate deleted successfully.");
		}
		return new Notification (false, "Candidate not found.");
	}


	@Override
	public Notification verifyEmailCandidate(String email, String code) {
		Optional<Candidates> candidateOpt = candidatesDao.findByEmail(email);
		if (candidateOpt.isEmpty()) {
			return new Notification(false,"Candidate not found.");
		}

		Candidates candidates = candidateOpt.get();
		boolean isVerified = mailVerificateService.verifyEmail(email, code);
		if (isVerified) {

			candidates.setActivateStatusEmail(true);
			candidatesDao.saveAndFlush(candidates);
			return new Notification(true,"Candidate verified successfully!");
		} else {
			return new Notification(false,"Invalid verification code or email!");
		}
	}

	@Override
	public Notification  verifyKYCCandidate(int id, MultipartFile document) {
		Optional<Candidates> candidateOpt = candidatesDao.findById(id);
		if (candidateOpt.isEmpty()) {
			return new Notification (false, "Candidate not found.");
		}

		Candidates candidate = candidateOpt.get();
		boolean kycResult = kycVerification.verifyCandidates(candidate);

		if (kycResult) {
			candidate.setActivateStatusKYC(true);
			candidatesDao.saveAndFlush(candidate);
			return new Notification (true, "KYC verified successfully.");
		} else{
			return new Notification (false, "Invalid request or missing file.");
		}
	}
}