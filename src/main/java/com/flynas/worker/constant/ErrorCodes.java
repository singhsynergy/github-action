package com.flynas.worker.constant;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import lombok.Getter;

@Getter
public enum ErrorCodes {

	 FIELD_REQUIRED("410")
	,FIELD_INVALID_DATA("411")
	,FIELD_INVALID_NUMERIC("412")
	,FIELD_INVALID_ALREADY_EXIST("413")
	,FIELD_INVALID_LENGTH("414")
	,FIELD_INVALID_ALPHA_NUMERIC("415")
	,NO_DATA_FOUND("416")
	,NOT_ACTIVE("417")
	,FIELD_INVALID_RANGE("418")
	,NOT_AUTHORIZED("419")
	,CANT_GREATER("420")
	,HEADER_MISSING("422");
	
	private final String code;

	ErrorCodes(String code) {
		this.code = code;
	}

	public String getMessage(MessageSource messageSource, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}

	public String getMessage(MessageSource messageSource, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

	public String getMessage(MessageSource messageSource) {
		return messageSource.getMessage(code, new Object[] {}, LocaleContextHolder.getLocale());
	}
}
