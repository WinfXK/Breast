package cn.winfxk.breast.form;

import cn.winfxk.breast.BreastException;

/**
 * @Createdate 2020/05/09 09:41:09
 * @author Winfxk
 */
public class FormException extends BreastException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7491432507167727106L;

	public FormException() {
		this("An unknown error occurred!Please send the error log to Winfxk@qq.com");
	}

	public FormException(String Message) {
		super(Message);
	}
}
