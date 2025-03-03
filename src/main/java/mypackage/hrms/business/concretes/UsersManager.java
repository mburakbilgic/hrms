package mypackage.hrms.business.concretes;

import mypackage.hrms.business.abstracts.UsersService;
import mypackage.hrms.core.VerificationService.VerificateMail.MailVerificateService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.dataAccess.abstracts.UsersDao;
import mypackage.hrms.entities.concretes.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsersManager implements UsersService {
    private UsersDao usersDao;
    private MailVerificateService mailVerificateService;

    @Autowired
    public UsersManager(UsersDao usersDao, MailVerificateService mailVerificateService) {
        this.usersDao = usersDao;
        this.mailVerificateService = mailVerificateService;
    }

    @Override
    public DataNotification<List<Users>> getAll() {
        List<Users> users = usersDao.findAll();
        return new DataNotification<> (users, true, "User retrieved successfully.");
    }

    @Override
    public Notification add(Users users) {
        if (usersDao.findByEmail(users.getEmail()).isPresent()) {
            return new Notification(false,"This email is already registered.");
        }

        users.setActivateStatus(false);
        usersDao.save(users);

        String verificationCode = String.valueOf(new Random().nextInt(900000) + 100000);
        mailVerificateService.sendVerificationEmail(users.getEmail(), verificationCode);;

        return new Notification(true,"User added. Verification email sent!");
    }

    @Override
    public Notification update(Users users) {
        if (usersDao.existsById(users.getId())) {
            usersDao.save(users);
            return new Notification (true, "User updated successfully.");
        }
        return new Notification (false, "User not found.");
    }

    @Override
    public Notification delete(int id) {
        Optional<Users> users = usersDao.findById(id);
        if (users.isPresent()) {
            usersDao.deleteById(id);
            return new Notification (true, "User deleted successfully.");
        }
        return new Notification (false, "User not found.");
    }

    @Override
    public Notification verifyEmail(String email, String code) {
        Optional<Users> usersOpt = usersDao.findByEmail(email);
        if (usersOpt.isEmpty()) {
            return new Notification(false,"User not found.");
        }

        Users users = usersOpt.get();
        boolean isVerified = mailVerificateService.verifyEmail(email, code);
        if (isVerified) {
            users.setActivateStatus(true);
            usersDao.save(users);
            return new Notification(true,"Email verified successfully!");
        } else {
            return new Notification(false,"Invalid verification code!");
        }
    }

}