package com.lifencoding.service;

import java.util.ArrayList;

public interface ContentServiceImpl <T>{
	public ArrayList<T> getList(T entity);

	public T get(T entity) throws Exception;

	public void add(T entity) throws Exception;

	public void modify(T entity) throws Exception;

	public void delete(T entity) throws Exception;
}
