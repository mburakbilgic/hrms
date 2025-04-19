package mypackage.hrms.business.abstracts;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.entities.concretes.Candidates;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface CandidatesService {
	DataNotification<List<Candidates>> getAll();
	Notification add(Candidates candidate);
	Notification update(Candidates candidate);
	Notification delete(int id);
	Notification verifyKYCCandidate(int id, MultipartFile document);
	Notification verifyEmailCandidate(String email, String code);

}