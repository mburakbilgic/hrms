package mypackage.hrms.business.abstracts;

import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.Employers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployersService {
	DataNotification<List<Employers>> getAll();
	Notification add(Employers employer);
	Notification update(Employers employer);
	Notification delete(int id);
	Notification verifyKYCEmployers(int id, MultipartFile document);
	Notification verifyEmailEmployers(String email, String code);
	DataNotification<Employers> findByTaxNumber(String taxNumber);
}