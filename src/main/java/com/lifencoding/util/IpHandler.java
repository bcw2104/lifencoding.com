package com.lifencoding.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

public class IpHandler {

	@Value("${key.whois}")
	private String whoisKey;

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
