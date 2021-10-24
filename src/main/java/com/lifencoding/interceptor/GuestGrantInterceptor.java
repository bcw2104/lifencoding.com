package com.lifencoding.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lifencoding.entity.GuestVO;
import com.lifencoding.serviceImpl.GuestService;
import com.lifencoding.util.RequestHeaderTool;

public class GuestGrantInterceptor implements HandlerInterceptor{

	@Autowired
	public GuestService guestService;
	@Autowired
	public RequestHeaderTool requestHeaderTools;

	@SuppressWarnings("deprecation")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Cookie guestCookie = null;
		boolean recordIp = false;

		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("visit")) {
					guestCookie = cookie;
					break;
				}
			}
		}

		if(guestCookie == null) {
			guestCookie = new Cookie("visit", String.valueOf(System.currentTimeMillis()));
			recordIp = true;
		}
		else{
			Date cookieDate = new Date(Long.parseLong(guestCookie.getValue()));
			Date currentDate = new Date();

			if(cookieDate.getDay() != currentDate.getDay()) {
				recordIp = true;
			}

			guestCookie.setValue(String.valueOf(System.currentTimeMillis()));
		}

		if(recordIp) {
			String guestIp = requestHeaderTools.getIp(request);
			String agent = request.getHeader("user-agent");
			String countryCode = requestHeaderTools.getCountryCode(guestIp);

			if(!requestHeaderTools.isCrawler(agent) && countryCode.equals("KR")) {
				GuestVO guestVO = new GuestVO();
				guestVO.setGuestIp(guestIp);
				guestService.add(guestVO);
			}
		}

		guestCookie.setMaxAge(60*60*24);
		guestCookie.setPath("/");

		response.addCookie(guestCookie);

		return true;
	}
}
