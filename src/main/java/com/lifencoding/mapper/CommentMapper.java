package com.lifencoding.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.lifencoding.entity.CommentVO;

@Mapper
public interface CommentMapper {
	public ArrayList<CommentVO> select(CommentVO commentVO);

	public void insert(CommentVO commentVO);

	public void delete(CommentVO commentVO);
}
