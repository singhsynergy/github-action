package com.flynas.worker.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatusCode;

import lombok.Data;

@Data
public class NotFoundException extends Exception implements Serializable {
	private static final long serialVersionUID = 2L;
	private final String errorMessage;
	private final HttpStatusCode code;
	private final ExceptionEnum errorCode;
	private final String errorReason;
	private final String referenceError;

	public NotFoundException(String errorReason, HttpStatusCode httpStatusCode, String errorMessage,
			ExceptionEnum errorcode, String referenceError) {
		this.errorReason = errorReason;
		this.code = httpStatusCode;
		this.errorMessage = errorMessage;
		this.errorCode = errorcode;
		this.referenceError = referenceError;
	}
}
