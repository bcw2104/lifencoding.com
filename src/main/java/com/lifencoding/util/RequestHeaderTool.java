package com.lifencoding.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;


public class RequestHeaderTool {

	@Value("${key.whois}")
	private String whoisKey;
	private ArrayList<String> botList;

	public RequestHeaderTool() {
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

	public String getCountryCode(String ip) {
		JSONParser parser = new JSONParser();
		URL url = null;
		JSONObject ipData = null;

		try {
			url = new URL("http://whois.kisa.or.kr/openapi/ipascc.jsp?query="+ip+"&key="+whoisKey+"&answer=json");
		} catch (MalformedURLException e) {
			return "Invaild";
		}

		try {
			ipData =  (JSONObject) parser.parse(new InputStreamReader(url.openStream(),"UTF-8"));
		} catch (IOException | ParseException e) {
			return "Invaild";
		}
		ipData = (JSONObject) ipData.get("whois");
		String countryCode = ipData.get("countryCode").toString();

		return countryCode;
	}
}
