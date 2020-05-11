package cn.winfxk.breast;

/**
 * @author Winfxk
 */
public abstract class BreastException extends RuntimeException {
	public BreastException() {
		this("An unknown error occurred!Please send the error log to Winfxk@qq.com");
	}

	public BreastException(String Message) {
		super(Message);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
}
