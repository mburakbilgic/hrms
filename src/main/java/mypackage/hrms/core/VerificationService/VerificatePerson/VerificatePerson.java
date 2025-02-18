package mypackage.hrms.core.VerificationService.VerificatePerson;

import mypackage.hrms.entities.concretes.Candidates;
import mypackage.hrms.entities.concretes.HrmsPersonels;
import mypackage.hrms.entities.concretes.Employers;

public interface VerificatePerson {

	boolean verifyCandidates(Candidates checkUser);
	boolean verifyHrms(HrmsPersonels checkUser);
	boolean verifyEmployers(Employers checkUser);

}
