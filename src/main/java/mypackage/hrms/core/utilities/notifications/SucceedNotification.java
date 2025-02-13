package mypackage.hrms.core.utilities.notifications;

public class SucceedNotification extends Notification {

	public SucceedNotification() {
		super(true);
	}

	public SucceedNotification(String message) {
		super(true, message);

	}
}
