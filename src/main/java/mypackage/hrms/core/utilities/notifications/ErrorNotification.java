package mypackage.hrms.core.utilities.notifications;

public class ErrorNotification extends Notification {

	public ErrorNotification() {
		super(false);
	}

	public ErrorNotification(String message) {
		super(false, message);
	}

}
