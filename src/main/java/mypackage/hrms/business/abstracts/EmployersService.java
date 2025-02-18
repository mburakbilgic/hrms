package mypackage.hrms.business.abstracts;

import java.util.List;

import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.Employers;

public interface EmployersService {
	DataNotification<List<Employers>> getAll();
	Notification add(Employers employers);
	Notification update(Employers employers);
	Notification delete(int id);
	Notification verifyEmployers(int id);

}