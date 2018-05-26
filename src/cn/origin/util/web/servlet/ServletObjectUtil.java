package cn.origin.util.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletObjectUtil {
	public static final ThreadLocal<HttpServletRequest> REQUEST_THREADLOCAL = new ThreadLocal<HttpServletRequest>() ; 
	public static final ThreadLocal<HttpServletResponse> RESPONSE_THREADLOCAL = new ThreadLocal<HttpServletResponse>() ;
	public static void setRequest(HttpServletRequest request) {
		REQUEST_THREADLOCAL.set(request);
	}
	public static void setResponse(HttpServletResponse response) {
		RESPONSE_THREADLOCAL.set(response);
	}
	public static HttpServletRequest getRequest() {
		return REQUEST_THREADLOCAL.get() ;
	}
	public static HttpServletResponse getResponse() {
		return RESPONSE_THREADLOCAL.get() ;
	}
	public static void clear() {
		REQUEST_THREADLOCAL.remove(); 
		RESPONSE_THREADLOCAL.remove(); 
	}
}
