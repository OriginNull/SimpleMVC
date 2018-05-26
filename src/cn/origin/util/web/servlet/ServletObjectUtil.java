package cn.origin.util.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * request、response对象的设置、获取与清除
 */
public class ServletObjectUtil {
	public static final ThreadLocal<HttpServletRequest> REQUEST_THREADLOCAL = new ThreadLocal<HttpServletRequest>() ; 
	public static final ThreadLocal<HttpServletResponse> RESPONSE_THREADLOCAL = new ThreadLocal<HttpServletResponse>() ;

	/**
	 * request 对象的设置
	 * @param request
	 */
	public static void setRequest(HttpServletRequest request) {
		REQUEST_THREADLOCAL.set(request);
	}

	/**
	 * responser对象的设置
	 * @param response
	 */
	public static void setResponse(HttpServletResponse response) {
		RESPONSE_THREADLOCAL.set(response);
	}

	/**
	 * request对象的获取
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return REQUEST_THREADLOCAL.get() ;
	}

	/**
	 * response对象的获取
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return RESPONSE_THREADLOCAL.get() ;
	}

	/**
	 * 清除request、response对象
	 */
	public static void clear() {
		REQUEST_THREADLOCAL.remove(); 
		RESPONSE_THREADLOCAL.remove(); 
	}
}
