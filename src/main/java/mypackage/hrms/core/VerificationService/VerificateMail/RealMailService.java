package mypackage.hrms.core.VerificationService.VerificateMail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@Primary
public class RealMailService implements MailVerificateService {

    private final JavaMailSender mailSender;
    private final Map<String, String> verificationCodes = new HashMap<>();

    @Value("${spring.mail.username}")
    private String senderEmail;

    public RealMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendVerificationEmail(String email, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmail);
            helper.setTo(email);
            helper.setSubject("Email Verification");
            helper.setText("Your verification code is: " + code);

            mailSender.send(message);

            verificationCodes.put(email, code);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
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