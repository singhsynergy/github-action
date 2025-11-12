package  com.flynas.worker.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.common.utils.ToolBox;
import com.flynas.worker.constant.MessageConstant.ERROR_CONSTANT;
import com.flynas.worker.exception.BadRequestException;
import com.flynas.worker.exception.ExceptionEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SampleValidator {

	@Autowired
	private MessageSource messageSource;

	
	public void validateQueryparams(Integer offset, Integer limit, String sortBy, String order, String status)
			throws BadRequestException {

		if (!ToolBox.isEmptyObject(offset) && offset < 0) {
			throw new BadRequestException(ERROR_CONSTANT.MESSAGE_ERROR, HttpStatus.BAD_REQUEST,
					ERROR_CONSTANT.INVALID_QUERY_PARAM_OFFSET, ExceptionEnum.invalidQuery, "");
		}

		if (!ToolBox.isEmptyObject(limit) && limit <= 0) {
			throw new BadRequestException(ERROR_CONSTANT.MESSAGE_ERROR, HttpStatus.BAD_REQUEST,
					ERROR_CONSTANT.INVALID_QUERY_PARAM_LIMIT, ExceptionEnum.invalidQuery, "");
		}

		if (!ToolBox.isEmptyString(sortBy) && ToolBox.isEmptyObject(order)) {
			throw new BadRequestException(ERROR_CONSTANT.MESSAGE_ERROR, HttpStatus.BAD_REQUEST,
					ERROR_CONSTANT.MESSAGE_VALIDATION_ORDER_BY, ExceptionEnum.invalidQuery, "");
		}

		if (!ToolBox.isEmptyObject(order) && ToolBox.isEmptyString(sortBy)) {
			throw new BadRequestException(ERROR_CONSTANT.MESSAGE_ERROR, HttpStatus.BAD_REQUEST,
					ERROR_CONSTANT.MESSAGE_VALIDATION_SORT_BY, ExceptionEnum.invalidQuery, "");
		}
	}
}
