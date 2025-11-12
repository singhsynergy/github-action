package com.flynas.worker.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * This is GenericException class.
 */
@Getter
@Setter
public class GenericException extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;
	private final String errorMessage;
	private final HttpStatusCode code;
	private final String errorCode;
	private final String errorReason;
	private final String referenceError;
	private final transient Object[] params;
	
	public GenericException(String errorcode) {
		this.errorMessage = "";
		this.code = null;
		this.errorCode = errorcode;
		this.errorReason = "";
		this.referenceError = "";
		this.params = null;
	}
	
	public GenericException(String errorcode, Object[] params) {
		this.errorMessage = "";
		this.code = null;
		this.errorCode = errorcode;
		this.errorReason = "";
		this.referenceError = "";
		this.params = params;
	}

	public GenericException(String errorMessage, String errorcode, Object[] params) {
		this.errorMessage = errorMessage;
		this.code = null;
		this.errorCode = errorcode;
		this.errorReason = "";
		this.referenceError = "";
		this.params = params;
	}
	
	public GenericException(String errorReason, HttpStatusCode code,String errorMessage,String errorcode,String referenceError) {
		this.errorMessage = errorMessage;
		this.code = code;
		this.errorCode = errorcode;
		this.errorReason = errorReason;
		this.referenceError = referenceError;
		this.params = null;
	}

}