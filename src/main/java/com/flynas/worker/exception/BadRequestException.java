package com.flynas.worker.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatusCode;

import lombok.Data;
@Data
public class BadRequestException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String errorMessage;
	private final HttpStatusCode code;
	private final ExceptionEnum errorCode;
	private final String errorReason;
	private final String referenceError;
	
	
	public BadRequestException(String errorReason, HttpStatusCode code,String errorMessage,ExceptionEnum errorcode,String referenceError) {
		this.errorMessage = errorMessage;
		this.code = code;
		this.errorCode = errorcode;
		this.errorReason = errorReason;
		this.referenceError = referenceError;
	}
}
