<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isErrorPage = "true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />

<!-- Bootstrap4 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>


<link rel="stylesheet" href="/resources/css/common-min.css">
<link rel="stylesheet" href="/resources/css/font-min.css">

<title>Life &amp; Coding</title>
</head>

<body style="background-color: rgb(250, 250, 250)" class="body-font">
    <header>
    	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
    </header>
    <div class="container">
    	<div class="my-5 py-5">

    		<h1>${requestScope['javax.servlet.error.status_code']} Error!!</h1>
			<c:if test="${requestScope['javax.servlet.error.status_code'] == 401}">
				<span class="mr-2 font-18">접근 권한이 없는 페이지입니다.</span>
			</c:if>
			<c:if test="${requestScope['javax.servlet.error.status_code'] == 404}">
				<span class="mr-2 font-18">존재하지 않는 페이지입니다.</span>
			</c:if>
			<c:if test="${requestScope['javax.servlet.error.status_code'] == 405}">
				<span class="mr-2 font-18">허용되지 않는 페이지입니다.</span>
			</c:if>
			<c:if test="${requestScope['javax.servlet.error.status_code'] == 500}">
				<span class="mr-2 font-18">서버에 오류가 발생했습니다.</span>
			</c:if>
			<a href="/"><button type="button" class="btn btn-default" style="width:80px">홈으로</button></a>
		</div>
    </div>
    <footer>
    	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
    </footer>
</body>
</html>
