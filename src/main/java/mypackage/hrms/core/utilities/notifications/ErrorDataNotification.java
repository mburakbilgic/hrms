package mypackage.hrms.core.utilities.notifications;

public class ErrorDataNotification<T> extends DataNotification<T> {

	public ErrorDataNotification(T data) {
		super(data, false);
	}

	public ErrorDataNotification(T data, String message) {
		super(data, false, message);
	}

	public ErrorDataNotification(String message) {
		super(null, false, message);
	}

	public ErrorDataNotification() {
		super(null, false, null);
	}

}
