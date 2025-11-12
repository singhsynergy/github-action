package com.flynas.worker.constant;

public interface MessageConstant {

	public interface ERROR_CODE {
		String HTTPS_STATUS_BAD_REQUEST = "400";

		String CREATED_HTTPS_STATUS_CODE = "201";

		String HTTPS_STATUS_OK = "200";

		String HTTPS_STATUS_INTERNAL_SERVER_ERROR = "500";

		String HTTPS_STATUS_FORBIDDEN = "403";

		String HTTPS_STATUS_Unauthorized = "401";

	}
	
	public interface ERROR_CONSTANT {
		String MESSAGE_ERROR = "Error";
		String MESSAGE_INTERNAL_SERVER = "Internal Error.";
		String MESSAGE_INTERNAL_SERVER_REF_ERROR = "Internal Server Error.";
		String MESSAGE_INTERNAL_SERVER_CODE = "500";
		String USER_NOT_AUTHORIZED = "User is not authorized to perform this action.";
		String INVALID_QUERY_PARAM_OFFSET = "Invalid Offset in Query Parameter";
		String INVALID_QUERY_PARAM_LIMIT = "Invalid Limit in Query Parameter";
		String MESSAGE_VALIDATION_ORDER_BY = "'order' data is missing. If 'sortBy' data is entered then order data is also required to enter";
		String MESSAGE_VALIDATION_SORT_BY = "'sortBy' data is missing. If 'order' data is entered then sortBy value is also required to enter";
	}
	
}
