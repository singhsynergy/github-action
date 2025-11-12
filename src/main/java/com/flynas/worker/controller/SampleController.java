package com.flynas.worker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.annotations.Permissions;
import com.common.integration.service.TokenIntegrationService;
import com.common.model.response.UserAccessDetailsResponse;
import com.flynas.worker.constant.Constant;
import com.flynas.worker.constant.UrlConstant;
import com.flynas.worker.request.SampleCreateRequest;
import com.flynas.worker.request.SampleUpdateRequest;
import com.flynas.worker.response.SampleResponse;
import com.flynas.worker.service.SampleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(UrlConstant.Basic.BASE_URL + "/samples")
@Tag(name = "Samples Controller", description = "CRUD operations for Samples")
public class SampleController {

	@Autowired
	private SampleService sampleService;

	@Autowired
	private TokenIntegrationService tokenIntegrationService;

	@Operation(summary = "Create Sample")
	@PostMapping
	//@Permissions(values = { UrlConstant.Permission_Constant.CREATE_USER_SAMPLE })
	public ResponseEntity<SampleResponse> addSample(
			@RequestHeader(name = UrlConstant.Header.AUTHORIZATION, required = false) String authorizationToken,
			@RequestHeader(name = UrlConstant.Header.CORRELATION_ID, required = false) String correlationId,
			@Valid @RequestBody SampleCreateRequest sampleCreateRequest) throws Exception {
		UserAccessDetailsResponse validateTokenResponse = tokenIntegrationService
				.validateTokenFromIS(authorizationToken);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(sampleService.addSample(authorizationToken, sampleCreateRequest, validateTokenResponse));
	}

	@Operation(summary = "Update Sample By Id")
	@PutMapping("/{id}")
	//@Permissions(values = { UrlConstant.Permission_Constant.UPDATE_USER_SAMPLE })
	public ResponseEntity<SampleResponse> updateSampleByUuid(
			@RequestHeader(name = UrlConstant.Header.AUTHORIZATION, required = false) String authorizationToken,
			@RequestHeader(name = UrlConstant.Header.CORRELATION_ID, required = false) String correlationId,
			@PathVariable(value = "id") String sampleUuid, @Valid @RequestBody SampleUpdateRequest sampleUpdateRequest)
			throws Exception {
		UserAccessDetailsResponse validateTokenResponse = tokenIntegrationService
				.validateTokenFromIS(authorizationToken);
		SampleResponse response = sampleService.updateSampleByUuid(authorizationToken, sampleUpdateRequest,
				validateTokenResponse, sampleUuid);
		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "Get Sample By Id")
	@GetMapping("/{id}")
	//@Permissions(values = { UrlConstant.Permission_Constant.GET_USER_SAMPLE })
	public ResponseEntity<SampleResponse> getSampleByUuid(
			@RequestHeader(name = UrlConstant.Header.AUTHORIZATION, required = false) String authorizationToken,
			@RequestHeader(name = UrlConstant.Header.CORRELATION_ID, required = false) String correlationId,
			@PathVariable(value = "id") String sampleUuid) throws Exception {
		return ResponseEntity.ok().body(sampleService.getSampleByUuid(authorizationToken, sampleUuid));
	}

	@Operation(summary = "Get All Samples")
	@GetMapping
	//@Permissions(values = { UrlConstant.Permission_Constant.GET_ALL_USERS_SAMPLE })
	public ResponseEntity<List<SampleResponse>> getAllSamples(
			@RequestHeader(name = UrlConstant.Header.AUTHORIZATION, required = false) String authorizationToken,
			@RequestHeader(name = UrlConstant.Header.CORRELATION_ID, required = false) String correlationId,
			@RequestParam(value = UrlConstant.Header.CUSTOMER_ID, required = false) String customerUuid,
			@RequestParam(value = Constant.ID, required = false) String sampleUuid,
			@RequestParam(value = Constant.STATUS, required = false) String status,
			@RequestParam(value = Constant.OFFSET, required = false) Integer offset,
			@RequestParam(value = Constant.LIMIT, required = false) Integer limit,
			@RequestParam(value = Constant.SORTBY, required = false) String sortBy,
			@RequestParam(value = Constant.ORDER, required = false) String order,
			@RequestParam(value = Constant.MODIFIED_BY, required = false) String modifiedBy,
			@RequestParam(value = Constant.MODIFIED_ON, required = false) String modifiedOn,
			@RequestParam(value = Constant.CREATED_BY, required = false) String createdBy,
			@RequestParam(value = Constant.CREATED_ON, required = false) String createdOn) throws Exception {
		return sampleService.getAllSamples(authorizationToken, customerUuid, sampleUuid, status, offset, limit, sortBy,
				order, modifiedBy, modifiedOn, createdBy, createdOn);
	}

	@Operation(summary = "Delete Sample By id")
	@DeleteMapping("/{id}")
	//@Permissions(values = { UrlConstant.Permission_Constant.DELETE_USER_SAMPLE })
	public ResponseEntity<Object> deleteSampleByUuid(
			@RequestHeader(name = UrlConstant.Header.AUTHORIZATION, required = false) String authorizationToken,
			@RequestHeader(name = UrlConstant.Header.CORRELATION_ID, required = false) String correlationId,
			@PathVariable(value = "id") String sampleUuid) throws Exception {
		UserAccessDetailsResponse validateTokenResponse = tokenIntegrationService
				.validateTokenFromIS(authorizationToken);
		sampleService.deleteSampleByUuid(sampleUuid, validateTokenResponse);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
