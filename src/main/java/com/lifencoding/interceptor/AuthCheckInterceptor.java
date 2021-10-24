package com.lifencoding.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lifencoding.annotation.Auth;


public class AuthCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if( handler instanceof HandlerMethod == false ) return true;

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 권한이 필요없는 경우
		if(auth == null) return true;

		HttpSession session = request.getSession();

		if(session.getAttribute("admin") == null ) {
			response.sendError(401);
			return false;
		}

		return true;
	}
}
