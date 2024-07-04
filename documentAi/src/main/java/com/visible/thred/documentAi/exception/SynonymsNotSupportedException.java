package com.visible.thred.documentAi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SynonymsNotSupportedException extends Exception {

	private static final long serialVersionUID = 1L;

	public SynonymsNotSupportedException() {
		super();
	}

	public SynonymsNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SynonymsNotSupportedException(String message) {
		super(message);
	}

	public SynonymsNotSupportedException(Throwable cause) {
		super(cause);
	}

}
