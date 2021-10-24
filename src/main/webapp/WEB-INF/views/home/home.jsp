<%@page import="com.lifencoding.util.GlobalValues"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/resources/css/home/style-min.css">
<script type="text/javascript" src="/resources/javascript/pagination/pagination.js"></script>
<script src="/resources/javascript/home/ui-action-min.js"></script>

<div class="cdn-link d-none"><%= GlobalValues.link %></div>
<div class="container-fluid p-0 head-font">
	<div class="banner position-relative bg-dark">
		<h1 class="banner-title position-absolute text-white font-28">일상과 배움을 기록하는 공간</h1>
   	</div>
</div>

<div class="container my-5">
	<jsp:include page="/WEB-INF/views/category/view.jsp"></jsp:include>
	<div class="bg-white shadow p-sm-5 p-0 row m-0">
		<div class="col-lg-4 mb-3 p-0">
			<div class="row m-0">
				<div class="col-sm-4 col-lg-12 ">
					<img alt="profile"  class="profile-img mb-3" src="<%=GlobalValues.profileImg %>${requestScope.adminInfo.adminImg}"/>
				</div>
				<div class="col-sm-1"></div>
				<div class="col-8 col-sm-7 col-lg-12">
					<div class="font-18">${requestScope.adminInfo.adminNickname }</div>
					<div class="mb-1 font-14">${requestScope.adminInfo.adminComment}</div>
					<div class="font-14">
						<a class="text-dark" href="https://mail.naver.com/write/popup/?orderType=new&to=bcw2104@gmail.com">Contact</a>
						<span class="mx-2">|</span>
						<a class="text-dark" href="https://github.com/bcw2104">Github</a>
					</div>
				</div>
			</div>
			<hr/>
			<div>
				<div class="card-deck">
					<div class="card">
						<div class="card-body text-center">
							<div class="card-title">전체 방문자 수</div>
							<div class="text-primary font-32">${requestScope.totalVisit}</div>
						</div>
					</div>
					<div class="card">
						<div class="card-body text-center">
							<div class="card-title">오늘 방문자 수</div>
							<div class="text-primary font-32">${requestScope.todayVisit}</div>
						</div>
					</div>
				</div>
			</div>
			<hr/>
		</div>
		<div class="col-lg-1"></div>
		<div class="col-lg-7 p-2">
			<div class="content-hot">
		    	<div class="mb-2 bg-dark text-white p-2 head-font font-20">인기 글</div>
				<div class="content-hot-list"></div>
		    </div>
			<div class="content-recent mt-5">
				<div id="recent" class="mb-2 bg-dark text-white p-2 head-font font-20">최신 글</div>
				<div class="content-recent-list"></div>
				<div class="pagination" class="mt-4"> </div>
		    </div>
	    </div>
	</div>
</div>