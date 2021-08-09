package com.lifencoding.util;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

public class RequestHeaderTools {

	private ArrayList<String> botList;

	public RequestHeaderTools() {
		botList = new ArrayList<String>();

		botList.add("yeti");
		botList.add("googlebot");
		botList.add("bingbot");
		botList.add("daum");

	}

	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-simplexi");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
	}

	public boolean isCrawler(String userAgent) {
		userAgent = userAgent.toLowerCase();

		Iterator<String> botIt = botList.iterator();

		while(botIt.hasNext()) {
			if(userAgent.contains(botIt.next()))
				return true;
		}

		return false;
	}
}
