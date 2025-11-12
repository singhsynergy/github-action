package com.flynas.worker.config;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.common.utils.ToolBox;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 * 
 * This is LoggingFilter class.
 */
@AllArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

	private final String responseHeader;
	private final String mdcTokenKey;
	private final String requestHeader;

	public LoggingFilter(String mdcTokenKey, String correlationKey) {
		this.mdcTokenKey = mdcTokenKey;
		this.requestHeader = correlationKey;
		this.responseHeader = correlationKey;
	}

	/**
	 * This method is used to provide HttpServletRequest and HttpServletResponse
	 * arguments instead of the default ServletRequest and ServletResponse ones.
	 * 
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			final String token;
			if (!ToolBox.isEmptyString(requestHeader) && !ToolBox.isEmptyString(request.getHeader(requestHeader))) {
				token = request.getHeader(requestHeader);
			} else {
				token = ToolBox.getUUID();
			}
			MDC.put(mdcTokenKey, token);

			if (!ToolBox.isEmptyString(responseHeader)) {
				response.addHeader(responseHeader, token);
			}
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove(mdcTokenKey);
		}
	}
}