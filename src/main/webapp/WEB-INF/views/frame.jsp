<%@page import="com.lifencoding.util.GlobalValues"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv='content-language' content='ko'>

<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
<meta name="viewport" content="width=device-width, initial-scale=1" >
<meta name="naver-site-verification" content="cafe74f69dba36a0a355cc0abb68cbaa91d7cd9b" >
<meta name="author" content="bcw2104">
<meta name="keywords" content="Life & Coding,lifencoding,life,coding,blog,코딩,일상,블로그">
<meta name="description" content="${requestScope.currentPost != null ? requestScope.postTumbnail : '일상과 배움을 기록하는 공간, bcw2104의 개인 블로그'}">

<meta property="og:type" content="${requestScope.currentPost != null ? 'article':'website'}">
<meta property="og:title" content="${requestScope.currentPost != null ? requestScope.currentPost.postTitle : 'Life & Coding'}">
<meta property="og:description" content="${requestScope.currentPost != null ? requestScope.postTumbnail : '일상과 배움을 기록하는 공간, bcw2104의 개인 블로그'}" >
<meta property="og:image" content="<%=GlobalValues.link%>${requestScope.currentPost != null && requestScope.currentPost.thumbnail != null ? '/resources/upload/files/post/'+=requestScope.currentPost.postId+='/thumbnail/'+=requestScope.currentPost.thumbnail : '/resources/images/og_img.png'}">
<meta property="og:url" content="https://www.lifencoding.com${requestScope['javax.servlet.forward.request_uri']}"/>
<meta property="og:site_name" content="Life & Coding">

<title>${requestScope.currentPost != null ? requestScope.currentPost.postTitle+=' | Life & Coding' : 'Life & Coding | 일상과 배움을 기록하는 공간'}</title>

<link rel="canonical" href="https://www.lifencoding.com${requestScope['javax.servlet.forward.request_uri']}">
<link rel="shortcut icon" href="https://www.lifencoding.com/favicon.ico">

<!-- Bootstrap4 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="/resources/css/common-min.css">
<link rel="stylesheet" href="/resources/css/font-min.css">

</head>

<body style="background-color: rgb(250, 250, 250)" class="body-font">
	<c:if test="${sessionScope.admin}">
		<jsp:include page="/WEB-INF/views/category/modal.jsp"></jsp:include>
	</c:if>
	<header>
    	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
    </header>

	<div>
		<jsp:include page="${requestScope.content}"></jsp:include>
    </div>
    <footer>
    	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
    </footer>
</body>
</html>
