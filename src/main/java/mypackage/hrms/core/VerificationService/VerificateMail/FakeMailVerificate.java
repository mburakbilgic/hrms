package mypackage.hrms.core.VerificationService.VerificateMail;

import org.springframework.stereotype.Service;

@Service
public class FakeMailVerificate implements MailVerificateService{

	@Override
	public boolean isEmail(String email) {
		return true;
	}

}
