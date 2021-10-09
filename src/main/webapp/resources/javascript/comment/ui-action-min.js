function getCommentList() {
	if($("#postId").val() != ''){
		$.ajax({
			url: "/comment/get.do?postId="+$("#postId").val(),
			type:"get",
			success: function(data){
				data = JSON.parse(data);
				$("#commentCount").text(data.length);
				for(i in data){
					$("#commentContainer").append(commentForm(data[i].id,data[i].nickname,data[i].content,data[i].date,data[i].parentId,data[i].isAdmin));
				}
			}
		});
	}
}

function postComment(e) {
	var formData = $(e).parent().prev().serialize();
	$.ajax({
		url: "/comment/add.do",
		type: "post",
		data : formData,
		success: function(data){
			if(data == "true"){
				alert("댓글이 등록되었습니다.");
				$("#commentContainer").empty();
				getCommentList();
				$("#commentForm").find("form")[0].reset();
			}
			else{
				alert("서버에 오류가 발생했습니다.");
			}
		}
	});
}

function deleteComment() {
	var formData = $("#deleteForm").find("form").serialize();
	$.ajax({
		url: "/comment/delete.do",
		type: "post",
		data : formData,
		success: function(data){
			if(data == "true"){
				alert("댓글이 삭제되었습니다.");
				$("#commentContainer").empty();
				getCommentList();
			}
			else{
				alert("비밀번호가 일치하지 않습니다.");
				$("#deleteForm").find("form")[0].reset();
			}
		}
	});
}

function deleteDynamicForm(){
	$("#replyForm").remove();
	$("#deleteForm").remove();
}

function createReplyForm(e){
	deleteDynamicForm();
	commentWriteForm(e,true);
}

function createDeleteForm(e){
	deleteDynamicForm();
	var container = $("<div>", {id:'deleteForm'});
	var form = $("<form>");

	form.append($("<input>", {type:'hidden', name:'commentId', value:$(e).parent().parent().parent().attr("id")}));

	container.append(form);
	$(e).parent().parent().parent().append(container);

	$.ajax({
		url: "/admin/state",
		type: "get",
		success: function(data){
			if(data == "true"){
				deleteComment(this);
			}
			else{
				var passwordForm ="<div class='form-group row mx-0'>"
								+"<div class='input-group col-6 px-0'>"
									+"<input type='password' name='password' class='form-control' placeholder='비밀번호'/>"
									+"<div class='input-group-append'>"
										+"<button type='button' onclick='deleteComment()' class='btn btn-dark'>삭제</button>"
									+"</div>"
								+"</div>"
							+"</div>"

				container.attr("class","comment-delete-box mt-3 px-2 py-3");
				form.append(passwordForm);
			}
		}
	});
}

function commentWriteForm(e,reply){
	var parent = "";
	var formType = "";
	if(reply){
		parent = "<input type='hidden' name='parentId' value='"+$(e).parent().attr("id")+"'/>";
		formType = "id='replyForm'";
	}
	else{
		formType = "id='commentForm'"
	}

	var info  = "<div class='form-group row mx-0'>"
						+"<div class='col-6 pl-0'>"
							+"<input type='text' name='nickname' class='form-control' placeholder='닉네임'/>"
						+"</div>"
						+"<div class='col-6 px-0'>"
							+"<input type='password' name='password' class='form-control' placeholder='비밀번호'/>"
						+"</div>"
					+"</div>";

	$.ajax({
		url: "/admin/state",
		type: "get",
		success: function(data){
			if(data == "true")
				info = "";

			var form = "<div " +formType+ " class='comment-write-box mt-3 px-2 py-3'>"
				+"<form>"
					+"<input type='hidden' name='postId' value='"+$("#postId").val()+"'/>"
					+ parent + info
					+"<div class='form-group'>"
						+"<textarea class='form-control'  name='content' rows='5' placeholder='내용을 입력해주세요.'></textarea>"
					+"</div>"
				+"</form>"
				+"<div class='text-right'>"
					+"<button type='button' onclick='postComment(this)' class='btn btn-dark'>등록</button>"
				+"</div>"
			+"</div>";

			$(e).after(form);
		}
	});
}
function commentForm(commentId,commentNickname,commentContent,commentDate,parentId,isAdmin){
	var commentMenu="";
	var link = $("#cdnLink").val();

	if(commentContent != ""){
		commentMenu = "<img class='comment-menu float-right dropdown-toggle' data-toggle='dropdown' alt='comment_menu' src='"+link+"/resources/images/icon_menu.png'>"
						+"<div class='dropdown-menu'>"
							+"<a class='dropdown-item' href='#none' onclick='createDeleteForm(this)'>삭제</a>"
							+"<a class='dropdown-item' href='#'>신고</a>"
						+"</div>";
	}else{
		commentContent = "삭제된 댓글입니다.";
	}

	var form = "<div class='comment-data'>"
					+"<span class='comment-author'>"+commentNickname+"</span>"
					+"<span class='comment-date text-secondary mx-2 font-13 body-font-light'>"+commentDate+"</span>"
					+commentMenu
				+"</div>"
				+"<div class='comment-content body-font-light pr-5 py-2'>"+commentContent+"</div>";

	if(parentId != commentId){
		form = "<div class='sub-comment px-5'>"
				+"<div id="+commentId+" class='comment-box px-2 py-3'>"
				+form
				+"</div>"
				+"</div>"
	}
	else{
		form = "<div id="+commentId+" class='comment-box px-2 py-3'>"
				+form
				+"<a class='font-15 text-secondary cursor-pointer' href='#none' onclick='createReplyForm(this)'>답글</a>"
				+"</div>";
	}

	return form;
}

$(document).ready(function() {
	getCommentList();
	commentWriteForm($("#commentContainer").after(),false);
});