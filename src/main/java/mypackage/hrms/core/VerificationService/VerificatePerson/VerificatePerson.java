package mypackage.hrms.core.VerificationService.VerificatePerson;

import mypackage.hrms.entities.concretes.Candidates;
import mypackage.hrms.entities.concretes.HrmsPersonels;

public interface VerificatePerson {

	boolean verifyCandidates(Candidates checkUser);
	boolean verifyHrms(HrmsPersonels checkUser);

}
