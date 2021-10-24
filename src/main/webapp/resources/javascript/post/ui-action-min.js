
function hidePostList(){
	$(".post-list-wrap").toggleClass("d-none");
	if ($("#postToggler").text() == "펼치기")
		$("#postToggler").text("접어두기")
	else
		$("#postToggler").text("펼치기")
}

function deletePost(){
	$.ajax({
		url: "/post/"+$("#postId").text()+"/delete.do",
		type:"post",
		success:function(redirect){
			window.location.href = redirect;
		}
	});
}

function shareToggler() {
	$("#shareTab").toggleClass("d-none");
}

function shareTwitter() {
	var sendText = $("#postTitle").text();
	var sendUrl = location.href;
	window.open("https://twitter.com/intent/tweet?text=" + sendText + "&url=" + sendUrl);
}

function shareFacebook() {
	var sendUrl = location.href;
	window.open("http://www.facebook.com/sharer/sharer.php?u=" + sendUrl);
}

function shareKakao() {
	Kakao.init('263fba347c2d0c35bd0540d4416e2105');

	Kakao.Link.createDefaultButton({
		container: '#btnKakao',
		objectType: 'feed',
		content: {
			title: $("#postTitle").text(),
			description: $('meta[name="description"]').attr('content'),
			imageUrl: $('meta[property="og:image"]').attr('content'),
			link: {
				mobileWebUrl: location.href,
				webUrl: location.href
			}
		}
	});
}

function shareNaver() {
	var sendText = encodeURI($("#postTitle").text());
	var sendUrl = encodeURI(encodeURIComponent(location.href));
	window.open(shareURL = "https://share.naver.com/web/shareView?url=" + sendUrl + "&title=" + sendText);
}

function getParams(){
    var queryString = location.search;
    var array = queryString.substring(1).split('&');
	var ret = {};
    for(var i = 0; i < array.length; i++){
		var temp = array[i].split('=');
		ret[temp[0]]=decodeURI(temp[1]);
	}
    return ret;
}

function timestampToDate(timestamp) {
	var date = new Date(timestamp);
	var today = new Date();

    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();

    month = month >= 10 ? month : '0' + month;
    day = day >= 10 ? day : '0' + day;
    hour = hour >= 10 ? hour : '0' + hour;
    minute = minute >= 10 ? minute : '0' + minute;
    second = second >= 10 ? second : '0' + second;

	today.setHours(0);
	today.setMinutes(0);
	today.setSeconds(0);
	today.setMilliseconds(0);

	if(today.getTime()> date.getTime()){
		return date.getFullYear() + '-' + month + '-' + day
	}
	else{
	    return hour + ':' + minute;
	}
}

function createPostItem(data){
	html = '<tr>'
			    +'<td class="text-left">'
				+'<a class="text-dark" href="/'+data.categoryEn+'/'+data.postId+'#post">'+data.postTitle+'</a>'
			+'</td>'
			+'<td class="text-right">'
				+'<span class="text-secondary">'+timestampToDate(data.postDate)+'</span>'
			+'</td>'
			+'</tr>'

	return html;
}

$(document).ready(function() {
	var params = getParams();
	var search = (params["search"] == undefined ? "" : "?search="+params["search"]);
	params["p"] = (params["p"] == undefined ? 1 : params["p"]);

	if ($("#btnKakao").length == 1) {
		shareKakao();
	}

	$("#postDeleteBtn").click(function() {
		var ans = confirm("포스트를 삭제하시겠습니까?");
		if(ans){deletePost()};
	});

	$('.post-list-wrap >.pagination').pagination({
		dataSource: function(done) {
		    $.ajax({
		        type: "get",
		        url: "/post/list.do",
				data : {'id':$("#categoryId").text(),'search': params["search"]},
		        success: function(response) {
		            done(response);
		        }
		    });
		 },
		pageNumber:params["p"],
		pageSize: 5,
	    callback: function(data, pagination) {
			if (typeof (history.pushState) != undefined) {
				var renewURL = location.href.split("?")[0];
				var queryString = (search=="" ? "?" : search+"&")+"p="+pagination.pageNumber;
		        history.pushState(null, null, renewURL+queryString);
    		}
			$("#postCnt").text("("+pagination.totalNumber+")");
			$(".post-list").empty();
			var html;
			for(var i in data){
				html = createPostItem(data[i]);

				$(".post-list").append(html);
			}
	    }
	});
});