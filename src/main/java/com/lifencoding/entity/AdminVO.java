package com.lifencoding.entity;

public class AdminVO {
	private String adminId;
	private String adminPw;
	private String adminEmail;
	private String adminNickname;
	private String adminComment;
	private String adminImg;
	private String salt;

	public AdminVO() {
		adminId = null;
		adminPw = null;
		adminEmail = null;
		adminNickname = null;
		adminComment = null;
		adminImg = null;
		salt = null;
	}

	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminPw() {
		return adminPw;
	}
	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminNickname() {
		return adminNickname;
	}
	public void setAdminNickname(String adminNickname) {
		this.adminNickname = adminNickname;
	}
	public String getAdminComment() {
		return adminComment;
	}

	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}
	public String getAdminImg() {
		return adminImg;
	}

	public void setAdminImg(String adminImg) {
		this.adminImg = adminImg;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
