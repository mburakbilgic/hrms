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

@Service
public class UsersManager implements UsersService {
    private UsersDao usersDao;
    private MailVerificateService mailVerificateService;

    @Autowired
    public UsersManager(UsersDao usersDao) {
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
        usersDao.save(users);
        return new Notification(true);
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

}