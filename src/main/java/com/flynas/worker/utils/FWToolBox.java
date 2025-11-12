package  com.flynas.worker.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;

import com.common.utils.ToolBox;
import com.flynas.worker.constant.Constant;
 

public class FWToolBox {

	private FWToolBox() {
	}

	public static String setNextPage(Long totalCount, Integer offset, Integer limit, Integer size) {
		String nextPage = null;
		if (offset == null) {
			offset = Constant.DEFAULT_OFFSET;
		}
		if (limit == null) {
			limit = size;
		}
		nextPage = FWToolBox.isNextPageRecordPresent(totalCount, offset, limit, size);
		return nextPage;
	}

	public static String isNextPageRecordPresent(Long total, Integer offset, Integer limit, int size) {

		Integer leftRecord = 0;

		if (total > 0 && limit > 0 && size > 0 && total != size) {
			leftRecord = (int) (total / limit);
		}
		if (leftRecord > offset) {

			return "true";
		}
		return "false";
	}

	public static void setHeaders(HttpHeaders headers, Integer offset, Integer limit, Long totalCount, Integer size) {
		headers.add("X-Total-Count", String.valueOf(totalCount));
		headers.add("X-Result-Count", String.valueOf(size));
		if (offset == null) {
			offset = Constant.DEFAULT_OFFSET;
		}
		if (limit == null) {
			limit = size;
		}
		headers.add("Offset", String.valueOf(offset));
		headers.add("Limit", String.valueOf(limit));
	}

	public static Pageable getPageableObj(Integer offset, Integer limit, String sortBy, String order, Long totalCount) {
		Pageable pageable = null;
		if (!ToolBox.isEmptyString(sortBy) && !ToolBox.isEmptyObject(order)) {
			pageable = setPaginationWithSorting(offset, limit, sortBy, order, totalCount);
		} else {
			pageable = setPaginationWithSorting(offset, limit, Constant.MODIFIED_ON, Constant.DESCENDING, totalCount);
		}
		return pageable;
	}

	public static Pageable setPaginationWithSorting(Integer offset, Integer limit, String sortBy, String order,
			Long totalCount) {
		Pageable pageable = null;
		if (offset == null) {
			offset = Constant.DEFAULT_OFFSET;
		}
		if (limit == null) {
			if (totalCount > 0) {
				limit = totalCount.intValue();
			} else {
				limit = Constant.DEFAULT_LIMIT;
			}
		}
		if (order.equalsIgnoreCase("asc")) {
			pageable = PageRequest.of(offset, limit, Sort.by(Order.asc(sortBy)));
		} else {
			pageable = PageRequest.of(offset, limit, Sort.by(Order.desc(sortBy)));
		}
		return pageable;
	}
}
