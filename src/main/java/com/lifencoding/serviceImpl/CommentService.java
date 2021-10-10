package com.lifencoding.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifencoding.entity.CommentVO;
import com.lifencoding.mapper.CommentMapper;
import com.lifencoding.service.ContentServiceImpl;
import com.lifencoding.util.SecureTool;

@Service
public class CommentService implements ContentServiceImpl<CommentVO>{

	@Autowired
	private SecureTool secureTool;
	@Autowired
	private CommentMapper commentMapper;

	@SuppressWarnings("unchecked")
	public JSONArray convertToJson(ArrayList<CommentVO> list) {
		JSONArray comments = new JSONArray();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for(CommentVO ele : list) {
			JSONObject object = new JSONObject();

			object.put("id", ele.getCommentId());
			object.put("nickname", ele.getCommentNickname());
			object.put("content", ele.getCommentContent());
			object.put("date", simpleDateFormat.format(ele.getCommentDate()));
			object.put("parentId", ele.getParentId());
			object.put("isAdmin", ele.getIsAdmin());

			comments.add(object);
		}

		return comments;
	}

	public boolean check(int commentId, String pw) throws Exception{
		CommentVO commentVO = new CommentVO();
		commentVO.setCommentId(commentId);
		commentVO = get(commentVO);

		if(commentVO.getCommentPw().equals(secureTool.encrypt(pw,commentVO.getSalt())))
			return true;
		else
			return false;
	}


	@Override
	public ArrayList<CommentVO> getList(CommentVO commentVO) {
		return commentMapper.select(commentVO);
	}

	@Override
	public CommentVO get(CommentVO commentVO) {
		ArrayList<CommentVO> list = commentMapper.select(commentVO);

		if(list.isEmpty()) {
			return null;
		}
		else {
			return list.get(0);
		}
	}

	@Override
	public void add(CommentVO commentVO) throws Exception {
		String salt = secureTool.createSalt();

		if(commentVO.getIsAdmin() == 0) {
			String encryptedPw = secureTool.encrypt(commentVO.getCommentPw(),salt);
			commentVO.setCommentPw(encryptedPw);
			commentVO.setSalt(salt);
		}
		commentMapper.insert(commentVO);
	}

	@Override
	public void delete(CommentVO commentVO) {
		commentMapper.delete(commentVO);
	}

	@Override
	public void modify(CommentVO commentVO) { }

}
