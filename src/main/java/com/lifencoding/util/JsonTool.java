package com.lifencoding.util;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTool <T>{

	public static<T> String objectToJson(T obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		String jsonObj = objectMapper.writeValueAsString(obj);

		return jsonObj;
	}

	public static<T> String arrayToJson(ArrayList<T> list) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<String> array = new ArrayList<String>();

		for(T ele : list) {
			String jsonObj = objectMapper.writeValueAsString(ele);
			array.add(jsonObj);
		}

		return array.toString();
	}
}
