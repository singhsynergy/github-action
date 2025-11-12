package com.flynas.worker.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.common.exception.GenericException;
import com.common.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flynas.worker.constant.Constant;
import com.flynas.worker.constant.ErrorCodes;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandlerApp {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException br) {
		log.info("GlobalExceptionHandlerApp->[handleBadRequest] Start");
		String errorReason = br.getErrorReason();
		String errorMessage = br.getErrorMessage();
		HttpStatusCode code = br.getCode();
		String errorCode = br.getErrorCode().getName();
		String referenceError = br.getReferenceError();
		ErrorResponse errorResponse = new ErrorResponse(errorReason, errorMessage, errorCode, referenceError);
		log.info("GlobalExceptionHandlerApp->[handleBadRequest] End");
		return ResponseEntity.status(code).body(errorResponse);

	}

	@ExceptionHandler({ GenericException.class })
	public ResponseEntity<ErrorResponse> handleBadRequest(GenericException br) {
		log.info("GlobalExceptionHandlerApp->[handleBadRequest] Start");
		String errorReason = "Unauthorized";
		HttpStatusCode code = HttpStatus.BAD_REQUEST;
		String errorCode = ExceptionEnum.missingCredentials.getName();
		if (br.getErrorCode() == "459") {
			code = HttpStatus.FORBIDDEN;
			errorReason = "Forbidden";
			errorCode = ExceptionEnum.invalidQuery.getName();
		} else if (br.getErrorCode() == "440") {
			code = HttpStatus.UNAUTHORIZED;
		}

		ObjectMapper mapper = new ObjectMapper();
		String[] msg = mapper.convertValue(br.getParams(), String[].class);
		String errorMessage = msg[0];

		String referenceError = "";

		ErrorResponse errorResponse = new ErrorResponse(errorReason, errorMessage, errorCode, referenceError);
		log.info("GlobalExceptionHandlerApp->[handleBadRequest] End");
		return ResponseEntity.status(code).body(errorResponse);

	}

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfx) {
		log.info("GlobalExceptionHandlerApp->[handleNotFoundException] Start");
		String errorReason = nfx.getErrorReason();
		String errorMessage = nfx.getErrorMessage();
		HttpStatusCode code = nfx.getCode();
		String errorCode = nfx.getErrorCode().getName();
		String referenceError = nfx.getReferenceError();
		ErrorResponse errorResponse = new ErrorResponse(errorReason, errorMessage, errorCode, referenceError);
		log.info("GlobalExceptionHandlerApp->[handleNotFoundException] End");
		return ResponseEntity.status(code).body(errorResponse);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		log.info("GlobalExceptionHandlerApp->[handleValidationErrors] Start");
		List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
		FieldError fieldError = null;
		String code = null;
		List<Object> list = new ArrayList<>();

		Optional<FieldError> fieldErrorExist = fieldErrors.stream().findFirst();
		if (fieldErrorExist.isPresent()) {
			fieldError = fieldErrorExist.get();
		}
		if (fieldError != null) {
			code = fieldError.getDefaultMessage();
			if (ErrorCodes.FIELD_INVALID_DATA.getCode().equals(code)) {
				list.add(fieldError.getField() + " " + "\'" + fieldError.getRejectedValue() + "\'");
			} else {
				list.add(fieldError.getField());
			}
		}

		if (ErrorCodes.FIELD_INVALID_RANGE.getCode().equals(code)) {
			list.add(fieldError.getArguments()[2]); // Min value
			list.add(fieldError.getArguments()[1]); // Max value
		} else if (ErrorCodes.FIELD_INVALID_LENGTH.getCode().equals(code)) {
			list.add(fieldError.getArguments()[1]);
		}

		String errorMessage = messageSource.getMessage(code, list.toArray(), LocaleContextHolder.getLocale());
		String errorReason = "Error";
		String errorCode = "InvalidBody";
		String referenceError = "";
		ErrorResponse errorResponse = new ErrorResponse(errorReason, errorMessage, errorCode, referenceError);
		log.info("GlobalExceptionHandlerApp->[handleValidationErrors] End");
		return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(errorResponse);

	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		log.info("GlobalExceptionHandlerApp->[getErrorsMap] Start");
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		log.info("GlobalExceptionHandlerApp->[getErrorsMap] End");
		return errorResponse;
	}

	@ExceptionHandler({ UnAuthorizedUserFoundException.class })
	public ResponseEntity<ErrorResponse> unAuthorizedUserFoundException(UnAuthorizedUserFoundException unu) {
		log.info("GlobalExceptionHandlerApp->[unAuthorizedUserFoundException] Start");
		String errorReason = org.springframework.http.HttpStatus.UNAUTHORIZED.getReasonPhrase();
		String errorMessage = null;
		if (unu.getErrorMessage() != null) {
			errorMessage = unu.getErrorMessage().get("error-message");
		}
		HttpStatusCode code = unu.getCode();
		String errorCode = unu.getErrorCode();
		String referenceError = unu.getReferenceError();
		ErrorResponse errorResponse = new ErrorResponse(errorReason, errorMessage, errorCode, referenceError);
		log.info("GlobalExceptionHandlerApp->[unAuthorizedUserFoundException] End");
		return ResponseEntity.status(code).body(errorResponse);

	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
		log.info("GlobalExceptionHandlerApp->[handleMissingParams] Start");
		String name = ex.getParameterName();
		String errorReason = org.springframework.http.HttpStatus.BAD_REQUEST.getReasonPhrase();
		String errorMessage = name + " parameter is mandatory";
		HttpStatusCode code = ex.getStatusCode();
		String errorCode = ExceptionEnum.invalidQuery.getName();
		String referenceError = "";
		ErrorResponse errorResponse = new ErrorResponse(errorReason, errorMessage, errorCode, referenceError);
		log.info("GlobalExceptionHandlerApp->[handleMissingParams] End");
		return ResponseEntity.status(code).body(errorResponse);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException ex) {
		log.info("GlobalExceptionHandlerApp->[handleNotFoundException] Start");
		String errorReason = Constant.Error.MESSAGE_QUERY_PARAM_REASON;
		String errorMessage = ex.getMessage();
		HttpStatusCode code = HttpStatus.BAD_REQUEST;
		String errorCode = String.valueOf(HttpStatus.BAD_REQUEST.value());
		String referenceError = "";
		ErrorResponse errorResponse = new ErrorResponse(errorReason, errorMessage, errorCode, referenceError);
		log.info("GlobalExceptionHandlerApp->[handleNotFoundException] End");
		return ResponseEntity.status(400).body(errorResponse);

	}

	@ExceptionHandler({ com.common.exception.RestTemplateException.class })
	public ResponseEntity<ErrorResponse> unAuthorizedUserFoundException(
			com.common.exception.RestTemplateException unu) {
		log.info("GlobalExceptionHandlerApp->[unAuthorizedUserFoundException] Start");
		String errorReason = org.springframework.http.HttpStatus.BAD_REQUEST.getReasonPhrase();
		String errorMessage = null;
		// com.common.model.response.ErrorResponse sau=
		// (com.common.model.response.ErrorResponse) unu.getErrorResponse();
		Object object = unu.getErrorResponse();
		ObjectMapper mapper = new ObjectMapper();
		com.common.model.response.ErrorResponseMessages errorResponseMessages = mapper.convertValue(object,
				com.common.model.response.ErrorResponseMessages.class);
		HttpStatusCode code = unu.getStatusCode();
		String errorCode = unu.getStatusCode().toString();
		String referenceError = "";
		errorMessage = errorResponseMessages.getErrorMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorReason, errorMessage, errorCode, referenceError);
		log.info("GlobalExceptionHandlerApp->[unAuthorizedUserFoundException] End");
		return ResponseEntity.status(code).body(errorResponse);
	}
}
