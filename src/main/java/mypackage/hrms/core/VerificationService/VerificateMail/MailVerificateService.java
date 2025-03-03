package mypackage.hrms.core.VerificationService.VerificateMail;

public interface MailVerificateService {
	boolean sendVerificationEmail(String email, String code);
	boolean verifyEmail(String email, String code);

}
