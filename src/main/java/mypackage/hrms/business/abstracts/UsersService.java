package mypackage.hrms.business.abstracts;

import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.Users;

import java.util.List;

public interface UsersService {
    DataNotification<List<Users>> getAll();
    Notification update(Users users);
    Notification delete(int id);
}