package com.flynas.worker.constant;

public interface Constant {
	String OFFSET = "offset";
	String LIMIT = "limit";
	String SORTBY = "sortBy";
	String ORDER = "order";
	Integer DEFAULT_OFFSET = 0;
	Integer DEFAULT_LIMIT = 50;
	String ID = "id";
	String STATUS = "status";
	String DESCENDING = "desc";
	String MODIFIED_BY = "modifiedBy";
	String MODIFIED_ON = "modifiedOn";
	String CREATED_BY = "createdBy";
	String CREATED_ON = "createdOn";
	String DATE = "yyyy-MM-dd";
	String TIMEFORMAT_YYYYMMDD_HHMMSSSSSX = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

	public interface Error {
		String MESSAGE_REASON = "An error has occurred.";
		String BAD_REQUEST = "Bad Request";
		String SUCCESS = "Success";
		String PARTIAL = "Partial";
		String FAIL = "Fail";
		String MESSAGE_UNAUTHORIZED_ERROR_MESSAGE = "Session expired. Please log in again.";
		String MESSAGE_QUERY_PARAM_REASON = "Invalid query parameter.";
		String MESSAGE_UNAUTHORIZED_REASON = "Unauthorized access.";
		String MESSAGE_INTERNAL_SERVER_CODE = "500 - Internal Server Error";
		String BAD_REQUEST_CODE = "400 - Bad Request";
	}

	public interface Status {
		String ACTIVE = "ACT";
		String INACTIVE = "INA";
		String DELETED = "DEL";
	}
}
