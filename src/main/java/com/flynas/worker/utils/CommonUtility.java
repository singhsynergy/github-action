package  com.flynas.worker.utils;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.common.integration.service.ISIntegrationService;
import com.common.utils.ToolBox;
import com.flynas.worker.constant.Constant;
import com.flynas.worker.constant.ErrorCodes;
import com.flynas.worker.constant.MessageConstant.ERROR_CONSTANT;
import com.flynas.worker.exception.BadRequestException;
import com.flynas.worker.exception.ExceptionEnum;
import com.flynas.worker.response.CreatedDetailResponse;
import com.flynas.worker.response.ModifiedDetailResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonUtility {

	@Value("${is.get.users.by.uuid.url}")
	private String isGetUsersByUuidUrl;

	@Autowired
	private ISIntegrationService isIntegrationService;

	@Autowired
	private MessageSource messageSource;
 
	public CreatedDetailResponse getCreatedDetail(String createdByName, String createdByUuid, Date createdOn) {
		CreatedDetailResponse createdDetail = new CreatedDetailResponse();
		createdDetail.setCreatedByUuid(createdByUuid);
		createdDetail.setHref(isGetUsersByUuidUrl + createdByUuid);
		createdDetail.setDate(ToolBox.getDateInTimeZoneFormat(createdOn));
		if (!ToolBox.isEmptyString(createdByName)) {
			createdDetail.setName(createdByName);
		}

		return createdDetail;
	}

	public ModifiedDetailResponse getModifiedDetail(String modifiedByName, String modifiedByUuid, Date modifiedOn) {
		ModifiedDetailResponse modifiedDetail = new ModifiedDetailResponse();
		modifiedDetail.setModifiedByUuid(modifiedByUuid);
		modifiedDetail.setHref(isGetUsersByUuidUrl + modifiedByUuid);
		modifiedDetail.setDate(ToolBox.getDateInTimeZoneFormat(modifiedOn));
		if (!ToolBox.isEmptyString(modifiedByName)) {
			modifiedDetail.setName(modifiedByName);
		}
		return modifiedDetail;
	}

	public String validateAndFormatDate(String dateStr, String dateType) throws BadRequestException {
		try {
			if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
				// Handling YYYY-MM-DD
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(dateStr, formatter);
				return date.format(formatter);
			} else {
				// Handling YYYYMMDD_HHMMSSSSSX
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.TIMEFORMAT_YYYYMMDD_HHMMSSSSSX);
				ZonedDateTime dateTime = ZonedDateTime.parse(dateStr, formatter);
				DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern(Constant.DATE);
				return dateTime.format(newFormatter);
			}
		} catch (Exception e) {
			log.error("validateAndFormatDate Error :: " + e);
			String errorMessage = messageSource.getMessage(ErrorCodes.FIELD_INVALID_DATA.getCode(),
					new Object[] { dateStr + " for " + dateType }, LocaleContextHolder.getLocale());
			throw new BadRequestException(ERROR_CONSTANT.MESSAGE_ERROR, HttpStatus.BAD_REQUEST, errorMessage,
					ExceptionEnum.invalidBody, "");
		}
	}
}
