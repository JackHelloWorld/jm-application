$(function() {
	$("#homeUrl").attr("href", '#' + encryptUrl('page/home'));
	initUrl();
	post(urlConfig.getMenus, {}, function(data) {
		actionMenus(data.data);
		selectMenus(location.href.split('#')[1]);
	}, function(data) {});
	
	var userInfo;
	
	if(getData('login_user')){
		userInfo = eval('('+getData('login_user')+')');
	}
	
	//导航栏信息
	navContentVue = new Vue({
		el : '#navContent',
		data : {
			userInfo : userInfo,
			
		}
	});
	
	var navContent2 = new Vue({
		el : '#navContent2',
		data : {
			userInfo : userInfo
		}
	});
	
});

var navContentVue;

var selectResourceId = null;

var logout = function() {
	post(urlConfig.userExit, {}, function(data) {
		layer.msg(data.msg, {
			icon: 1,
			time: 1000,
			end: function() {
				location.href = './login.html';
			}
		});
	}, function(data) {

	});
};
var initUrl = function() {
	var hrefs = location.href.split('#');
	if(hrefs.length < 2 || hrefs[1] == '') {
		location.href = hrefs[0] + $("#homeUrl").attr("href");
	}
};
var initMenus = function(menus, $li) {
	var $ul = $("<ul></ul>").addClass("treeview-menu");
	for(var i = 0; i < menus.length; i++) {
		var menu = menus[i];
		var li = $("<li></li>");
		var $a = $("<a></a>");
		var $i = $("<i></i>").addClass(menu.icon);
		var $span = $("<span></span>").html(menu.text);
		$a.attr("href", (menu.url == null || menu.url == '' ? 'javascript:;' : '#' + encryptUrl('page/' + menu.url)));
		$a.append($i).append($span);
		li.append($a);
		li.attr("resource_id",menu.id);
		$ul.append(li);
		$li.append($ul);
		if(menu.nodes != null && menu.nodes.length > 0) {
			li.addClass("treeview");
			$a.append('<span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>');
			initMenus(menu.nodes, li);
		} else {}
	}
};
var selectMenus = function(u) {
	var url = '#' + u;
	$("#menus-content li.active").removeClass('active').removeClass('menu-open');
	var aa = $("#menus-content a[href='" + url + "']").parents("li");
	selectResourceId = aa.attr("resource_id");
	$("#page-menu-bar").html("");
	var $li = $('<li></li>');
	var $a = $('<a href="#' + encryptUrl('page/home') + '"><i class="fa fa-dashboard"></i> 首页</a>');
	$li.append($a);
	$("#page-menu-bar").append($li)
	for(var i = aa.length - 1; i >= 0; i--) {
		var li = $('<li><a href="javascript:;">' + $(aa.get(i)).find("a:eq(0)").text() + '</a></li>');
		if(i == 0) {
			li.addClass('active');
		}
		$("#page-menu-bar").append(li);
	}
	$("#menus-content a[href='" + url + "']").parents("li").addClass('active').addClass('menu-open');
	$('.sidebar-menu').tree();
	var durl = 'none';
	try {
		durl = decodeUrl(u).substring(5);

		if(!durl || durl == '')
			durl = 'none';

	} catch(e) {}
	var indexLoadding = layer.load(1, {
		shade: [0.1, '#fff']
	});
	$.ajax({
		url: "./pages/" + durl + ".html",
		dataType: 'html',
		success: function(data) {
			layer.close(indexLoadding);
			$("#page-content").html(data);
			var title = $("#page-content").find("title").html();
			$("title:eq(0)").html(title);
			var desc = $("page-desc").html();
			$("#page-desc").html(desc);

			var pageTitle = $("page-title").html();
			$("#page-title").html(pageTitle);
			$("#page-content").find("title,page-desc,page-title").remove();
		},
		error: function(error) {
			layer.close(indexLoadding);
			$("#page-desc").html("");
			$("#page-title").html("");
			switch(error.status) {
				case 404:
					$("#page-content").html(error404);
					$("title:eq(0)").html("页面找不到");
					break;
				default:
					$("#page-content").html(error500);
					$("title:eq(0)").html("网络错误");
					break;
			}
		}
	});
};
var actionMenus = function(menus) {
	if(menus) {
		for(var i = 0; i < menus.length; i++) {
			var menu = menus[i];
			var li = $("<li></li>");
			var $a = $("<a></a>").append();
			var $i = $("<i></i>").addClass(menu.icon);
			var $span = $("<span></span>").html(menu.text);
			$a.attr("href", (menu.url == null || menu.url == '' ? 'javascript:;' : '#' + encryptUrl('page/' + menu.url)));
			$a.append($i).append($span);
			li.append($a);
			$('#menus-content').append(li);
			if(menu.nodes != null && menu.nodes.length > 0) {
				li.addClass("treeview");
				$a.append('<span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>');
				initMenus(menu.nodes, li);
			}
		}
	}
};
var encryptUrl = function(url) {
	return window.btoa(url);
};

var decodeUrl = function(url) {
	return window.atob(url);
};
window.addEventListener('hashchange', function() {
	var href = window.location.href.split("#")[1];
	if(href != null && href != '') {
		selectMenus(href);
	}
});
var error404 = '<div class="error-page"><h2 class="headline text-yellow"> 404</h2><div class="error-content"><h3><i class="fa fa-warning text-yellow"></i> 页面没有找到</h3><p>请尝试以下方式处理</p><ul class="list-unstyled spaced inline bigger-110 margin-15"><li><i class="fa fa-hand-o-right text-blue"></i>重新检查url是否有输入错误</li><li><i class="fa fa-hand-o-right text-blue"></i>阅读常见问题</li><li><i class="fa fa-hand-o-right text-blue"></i>联系管理员</li></ul></div></div>';
var error500 = '<div class="error-page"><h2 class="headline text-yellow"> 500</h2><div class="error-content"><h3><i class="fa fa-warning text-yellow"></i> 网络服务器繁忙</h3><p>请尝试以下方式处理</p><ul class="list-unstyled spaced inline bigger-110 margin-15"><li><i class="fa fa-hand-o-right text-blue"></i>请刷新后重试</li><li><i class="fa fa-hand-o-right text-blue"></i>阅读常见问题</li><li><i class="fa fa-hand-o-right text-blue"></i>联系管理员</li></ul></div></div>';
var initActions = function(success){
	if(selectResourceId){
		post(urlConfig.getAction,{parent_id:selectResourceId},function(data){
			success(data.data);
		},function(data){
			
		});
	}
}
