package mypackage.hrms.core.utilities.notifications;

public class Notification {

	private boolean success;
	private String message;

	public Notification(boolean success) {
		this.success = success;
	}

	public Notification(boolean success, String message) {
		this(success);
		this.message = message;
	}

	public boolean isSucceed() {
		return success;
	}

	public String getMessage() {
		return message;
	}

}
