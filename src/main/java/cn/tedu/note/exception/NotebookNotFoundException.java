package cn.tedu.note.exception;

public class NotebookNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 9146102806124686052L;

	public NotebookNotFoundException() {
		super();
	}

	public NotebookNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotebookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotebookNotFoundException(String message) {
		super(message);
	}

	public NotebookNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
	
}
