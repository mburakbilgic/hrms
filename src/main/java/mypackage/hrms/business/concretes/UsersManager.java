package mypackage.hrms.business.concretes;

import mypackage.hrms.business.abstracts.UsersService;
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

    @Autowired
    public UsersManager(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public DataNotification<List<Users>> getAll() {
        List<Users> users = usersDao.findAll();
        return new DataNotification<> (users, true, "User retrieved successfully.");
    }

    @Override
    public Notification update(Users users) {
        Optional<Users> existingUser = usersDao.findByEmail(users.getEmail());
        if (existingUser.isPresent()) {
            Users userToUpdate = existingUser.get();
            userToUpdate.setPassword(users.getPassword());
            usersDao.save(userToUpdate);
            return new Notification(true, "User password updated successfully.");
        }
        return new Notification(false, "User not found.");
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