package com.flynas.worker.constant;

public interface UrlConstant {

	public interface Basic {
		String BASE_URL = "/api/v1";

	}

	interface Header {
		String AUTHORIZATION = "Auth";
		String CONTENT_TYPE = "Content-Type";
		String CORRELATION_ID = "CorrelationId";
		String CUSTOMER_ID = "customerId";
		String BEARER = "Bearer ";
		String JSON = "application/json";
	}

	interface ParamConstant {
		String CORRELATION_ID = "CorrelationId";
	}
	
	public interface Permission_Constant {
		String CREATE_USER_SAMPLE = "create_user_sample";
		String UPDATE_USER_SAMPLE = "update_user_sample";
		String GET_USER_SAMPLE = "get_user_sample";
		String GET_ALL_USERS_SAMPLE= "get_all_users_sample";
		String DELETE_USER_SAMPLE = "delete_user_sample";
	}
}
