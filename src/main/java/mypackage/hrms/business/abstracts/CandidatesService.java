package mypackage.hrms.business.abstracts;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.entities.concretes.Candidates;

@Transactional
public interface CandidatesService {
	DataNotification<List<Candidates>> getAll();
	Notification update(Candidates candidate);
	Notification delete(int id);
	Notification verifyCandidate(int id);

}