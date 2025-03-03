package mypackage.hrms.core.VerificationService.VerificateMail;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class FakeMailVerificate implements MailVerificateService{

	private final Map<String, String> verificationCodes = new HashMap<>();

	@Override
	public boolean sendVerificationEmail(String email, String code) {
		verificationCodes.put(email, code);
		System.out.println("Fake email sent to: " + email + " with code: " + code);
		return true;
	}

	@Override
	public boolean verifyEmail(String email, String code) {
		if (verificationCodes.containsKey(email) && verificationCodes.get(email).equals(code)) {
			verificationCodes.remove(email);
			return true;
		}
		return false;
	}
}
