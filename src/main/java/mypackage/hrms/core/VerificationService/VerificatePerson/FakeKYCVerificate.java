package mypackage.hrms.core.VerificationService.VerificatePerson;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import mypackage.hrms.entities.concretes.Candidates;
import mypackage.hrms.entities.concretes.HrmsPersonels;
import mypackage.hrms.entities.concretes.Employers;

@Service
@Primary
public class FakeKYCVerificate implements VerificatePerson {

	@Override
	public boolean verifyCandidates(Candidates candidate) {
		return candidate.getNationalID() != 0 && candidate.getFirstName() != null && candidate.getLastName() != null;
	}

	@Override
	public boolean verifyHrms(HrmsPersonels hrmsPersonels) {
		return hrmsPersonels.getNationalID() != 0 && hrmsPersonels.getFirstName() != null && hrmsPersonels.getLastName() != null;
	}

	@Override
	public boolean verifyEmployers(Employers employers) {
		return employers.getCompanyName() != null && employers.getWebAdress() != null && employers.getPhoneNumber() != null;
	}
}
