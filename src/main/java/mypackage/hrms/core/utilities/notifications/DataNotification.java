package mypackage.hrms.core.utilities.notifications;

public class DataNotification<T> extends Notification {

	private T data;

	public DataNotification(T data, boolean success) {
		super(success);
		this.data = data;
	}

	public DataNotification(T data, boolean success, String message) {
		super(success, message);
		this.data = data;
	}

	public T getData() {
		return data;
	}

}
