package com.lifencoding.entity;

import java.sql.Timestamp;

public class CommentVO {
	private int commentId;
	private String commentNickname;
	private String commentPw;
	private String commentContent;
	private Timestamp commentDate;
	private int parentId;
	private int postId;
	private int isAdmin;

	public CommentVO() {
		commentId = 0;
		commentNickname = null;
		commentPw = null;
		commentContent = null;
		commentDate = null;
		parentId = 0;
		postId = 0;
		isAdmin = 0;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentNickname() {
		return commentNickname;
	}

	public void setCommentNickname(String commentNickname) {
		this.commentNickname = commentNickname;
	}

	public String getCommentPw() {
		return commentPw;
	}

	public void setCommentPw(String commentPw) {
		this.commentPw = commentPw;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Timestamp getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

}
