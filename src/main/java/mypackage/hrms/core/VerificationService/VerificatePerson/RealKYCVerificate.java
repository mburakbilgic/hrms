package mypackage.hrms.core.VerificationService.VerificatePerson;

import mypackage.hrms.entities.concretes.Candidates;
import mypackage.hrms.entities.concretes.Employers;
import mypackage.hrms.entities.concretes.HrmsPersonels;

public class RealKYCVerificate implements VerificatePerson {

    @Override
    public boolean verifyCandidates(Candidates candidate) {
        return true;
    }

    @Override
    public boolean verifyHrms(HrmsPersonels hrmsPersonels) {
        return true;
    }

    @Override
    public boolean verifyEmployers(Employers employers) {
        return true;
    }

}
