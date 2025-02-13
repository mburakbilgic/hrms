package mypackage.hrms.core.VerificationService.VerificatePerson;

import org.springframework.stereotype.Service;

import mypackage.hrms.entities.concretes.HrmsPersonels;

@Service
public class FakeMernisVerificate implements VerificatePerson{

	@Override
	public boolean isPerson(HrmsPersonels hrmsPersonels) {
		return true;
	}

}
