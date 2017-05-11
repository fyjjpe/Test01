package cn.tedu.note.exception;
/**
 * 自定义账号错误异常类
 * @author Administrator
 *
 */
public class NameException extends RuntimeException {
	private static final long serialVersionUID = 3051757784630921393L;
	public NameException() {
	}

	public NameException(String message) {
		super(message);
	}

	public NameException(Throwable cause) {
		super(cause);
	}

	public NameException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
