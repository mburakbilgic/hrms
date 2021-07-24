package mypackage.hrms.core.utilities.notifications;

public class SucceedDataNotification<T> extends DataNotification<T> {

	public SucceedDataNotification(T data) {
		super(data, true);
	}

	public SucceedDataNotification(T data, String message) {
		super(data, true, message);
	}

	public SucceedDataNotification(String message) {
		super(null, true, message);
	}

	public SucceedDataNotification() {
		super(null, true, null);
	}
	
}
