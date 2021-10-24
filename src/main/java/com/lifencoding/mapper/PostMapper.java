package com.lifencoding.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.lifencoding.entity.PostVO;

@Mapper
public interface PostMapper {

	public int getSequenceNum();

	public ArrayList<PostVO> select(PostVO postVO);

	public ArrayList<PostVO> selectNear(PostVO postVO);

	public ArrayList<PostVO> selectHot(int cnt);

	public void increaseHits(int postId);

	public void insert(PostVO postVO);

	public void modify(PostVO postVO);

	public void delete(int postId);
}
