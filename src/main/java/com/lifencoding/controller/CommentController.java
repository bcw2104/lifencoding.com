package com.lifencoding.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifencoding.entity.AdminVO;
import com.lifencoding.entity.CommentVO;
import com.lifencoding.serviceImpl.AdminService;
import com.lifencoding.serviceImpl.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private CommentService commentService;

	@GetMapping(value="/get.do", produces="application/text;charset=utf8")
	@ResponseBody
	public String get(@RequestParam("postId") String _postId) throws Exception {

		CommentVO commentVO = new CommentVO();

		commentVO.setPostId(Integer.parseInt(_postId));

		ArrayList<CommentVO> commentList = commentService.getList(commentVO);

		return commentService.convertToJson(commentList).toJSONString();
	}

	@PostMapping("/add.do")
	@ResponseBody
	public String add(@RequestParam("postId") String _postId,
						@RequestParam(name = "parentId",required = false) String _parentId,
						@RequestParam(name = "nickname",required = false) String commentNickname,
						@RequestParam(name = "password",required = false) String commentPw,
						@RequestParam("content") String commentContent,HttpSession session) throws Exception {

		CommentVO commentVO = new CommentVO();

		if(session.getAttribute("admin") != null) {
			AdminVO adminVO = adminService.getAdminInfo();
			commentVO.setCommentNickname(adminVO.getAdminNickname());
			commentVO.setCommentPw(adminVO.getAdminPw());
			commentVO.setIsAdmin(1);
		}
		else if(commentNickname != null && commentPw != null) {
			commentVO.setCommentNickname(commentNickname);
			commentVO.setCommentPw(commentPw);
		}
		else {
			return "false";
		}

		if(_parentId != null)
			commentVO.setParentId(Integer.parseInt(_parentId));

		commentVO.setPostId(Integer.parseInt(_postId));
		commentVO.setCommentContent(commentContent);

		commentService.add(commentVO);

		return "true";
	}

	@PostMapping("/delete.do")
	@ResponseBody
	public String modify(@RequestParam("commentId") String _commentId,
						 @RequestParam(name = "password",required = false) String commentPw,HttpSession session) throws Exception {

		int commentId = Integer.parseInt(_commentId);

		if(session.getAttribute("admin") != null || (commentPw != null && commentService.check(commentId, commentPw))) {
			CommentVO commentVO = new CommentVO();
			commentVO.setCommentId(commentId);

			commentService.delete(commentVO);

			return "true";
		}
		else {
			return "false";
		}
	}
}
