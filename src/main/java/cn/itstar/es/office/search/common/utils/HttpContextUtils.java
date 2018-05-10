package cn.itstar.es.office.search.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http上下文
 */
public class HttpContextUtils {

	public static HttpServletRequest getHttpServletRequest() {
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpServletResponse getHttpServletResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
}
