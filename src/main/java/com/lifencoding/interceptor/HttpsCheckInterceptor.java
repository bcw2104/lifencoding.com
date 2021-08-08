package com.lifencoding.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class HttpsCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String serverPort = request.getHeader("x-server_port");

		if (serverPort != null && serverPort.equals("443")) {
			return true;
		} else {
			String url = request.getRequestURL().toString();
			if (url.startsWith("http://")) {
				url = url.replace("http://", "https://");
				response.sendRedirect(url);
			}

			return false;
		}
	}
}
