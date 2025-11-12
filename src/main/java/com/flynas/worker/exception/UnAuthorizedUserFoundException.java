package com.flynas.worker.exception;

import java.io.Serializable;
import java.util.HashMap;

import org.springframework.http.HttpStatusCode;

import lombok.Data;
@Data
public class UnAuthorizedUserFoundException extends Exception implements Serializable {
	private static final long serialVersionUID = 2L;
	private final HashMap<String, String> errorMessage;
	private final HttpStatusCode code;
	private final String errorCode;
	private final String errorReason;
	private final String referenceError;
	private final HttpStatusCode reason;
	
	
	@SuppressWarnings("unchecked")
	public UnAuthorizedUserFoundException(String errorReason, HttpStatusCode code,Object errorMessage,String referenceError,String errorcode) {
		this.errorMessage = (HashMap<String, String>) errorMessage;
		this.code = code;
		this.errorCode = errorcode;
		this.errorReason = errorReason;
		this.referenceError = referenceError;
		this.reason = null;
	}
	
	
}
