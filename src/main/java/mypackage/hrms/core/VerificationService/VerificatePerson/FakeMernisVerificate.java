package mypackage.hrms.core.VerificationService.VerificatePerson;

import org.springframework.stereotype.Service;
import mypackage.hrms.entities.concretes.Candidates;
import mypackage.hrms.entities.concretes.HrmsPersonels;

@Service
public class FakeMernisVerificate implements VerificatePerson {

	@Override
	public boolean verifyCandidates(Candidates candidate) {
		return candidate.getNationalID() != 0 && candidate.getFirstName() != null && candidate.getLastName() != null;
	}

	@Override
	public boolean verifyHrms(HrmsPersonels hrmsPersonels) {
		return hrmsPersonels.getNationalID() != 0 && hrmsPersonels.getFirstName() != null && hrmsPersonels.getLastName() != null;
	}
}
