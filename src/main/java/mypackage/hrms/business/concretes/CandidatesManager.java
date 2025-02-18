package mypackage.hrms.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mypackage.hrms.business.abstracts.CandidatesService;
import mypackage.hrms.core.VerificationService.VerificateMail.MailVerificateService;
import mypackage.hrms.core.VerificationService.VerificatePerson.VerificatePerson;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.dataAccess.abstracts.CandidatesDao;
import mypackage.hrms.entities.concretes.Candidates;

@Service
public class CandidatesManager implements CandidatesService {

	private CandidatesDao candidatesDao;
	private VerificatePerson kycVerification;
	private MailVerificateService mailVerificateService;

	public CandidatesManager(CandidatesDao candidatesDao, VerificatePerson kycVerification, MailVerificateService mailVerificateService) {
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
	public Notification update(Candidates candidate) {
		if (candidatesDao.existsById(candidate.getId())) {
			candidatesDao.save(candidate);
			return new Notification (true, "Candidate updated successfully.");
		}
		return new Notification (false, "Candidate not found.");
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
	public Notification  verifyCandidate(int id) {
		Optional<Candidates> candidateOpt = candidatesDao.findById(id);
		if (candidateOpt.isEmpty()) {
			return new Notification (false, "Candidate not found.");
		}

		Candidates candidate = candidateOpt.get();
		boolean kycResult = kycVerification.verifyCandidates(candidate);
		// boolean mailResult = mailVerificateService.sendVerificationEmail(candidate.getEmail());
		boolean mailResult = true;

		if (kycResult && mailResult) {
			return new Notification (true, "Candidate verified successfully.");
		} else if (!kycResult) {
			return new Notification (false, "KYC verification failed.");
		} else {
			return new Notification (false, "Email verification failed.");
		}
	}
}