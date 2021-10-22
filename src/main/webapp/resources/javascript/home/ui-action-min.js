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

function createPostItem(cdnLink,data){
	html = '<div class="post-item my-4 d-flex pl-2">'
				+'<div class="p-0 flex-fill d-flex flex-column justify-content-between">'
					+'<div>'
						+'<a class="text-dark font-17" href="/'+data.categoryEn+'/'+data.postId+'">'+data.postTitle+'</a>'
						+'<div class="mt-3 font-13 post-content">'+data.postContent+'</div>'
					+'</div>'
					+'<div class="mt-3 font-12">'
						+'<span><a class="text-primary" href="/'+data.categoryEn+'">'+data.categoryName+'</a></span>'
						+'<span class="mx-2 text-secondary">|</span>'
						+'<span class="text-secondary">'+timestampToDate(data.postDate)+'</span>'
					+'</div>'
				+'</div>'
				+'<img alt="thumbnail" class="thumbnail ml-3" src="'+cdnLink+'/resources/upload/files/post/'+data.postId+'/thumbnail/'+data.thumbnail+'">'
			+'</div>'
			+'<hr/>';

	return html;
}

$(document).ready(function() {
	var params = getParams();
	var cdnLink = $(".cdn-link").text();
	params["p"] = (params["p"] == undefined ? 1 : params["p"]);

	$('.content-recent>.pagination').pagination({
		dataSource: function(done) {
		    $.ajax({
		        type: "get",
		        url: "/post/recent.do",
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
				var queryString = "?p="+pagination.pageNumber;
		        history.pushState(null, null, renewURL+queryString);
    		}
			$(".content-recent-list").empty();
			var html;
			for(var i in data){
				html = createPostItem(cdnLink,data[i]);

				$(".content-recent-list").append(html);
			}
	    }
	});
	$.ajax({
        type: "get",
        url: "/post/hot.do",
        success: function(data) {
			for(var i in data){
				html = createPostItem(cdnLink,data[i]);
				$(".content-hot-list").append(html);
			}
        }
    });
});