var contentVue;
$(function(){
	contentVue = new Vue({
		el : '#page-content',
		data : {
			param : {
				pageSize : 10,
				pageNumber : 1,
				nickName : '',
				loginName : '',
				roleName : '',
				phone : '',
			},
			page : {
				list : [],
				navigatepageNums : [],
			},
			nodata : {
				colspan : 8,
				text : '无匹配的用户信息'
			},
			roles : [],
			editData : {
				edit : false,
				nickName : '',
				loginName : '',
				roleName : '',
				adminDesc : '',
				roleId : '',
				phone : ''
			},
			actions : []
		},
		methods : {
			clickRow : function(index){
				if(index == this.page.selected){
					Vue.set(contentVue.page,'selected',null);
				}else{
					Vue.set(contentVue.page,'selected',index);
				}
			},
			changePageSize : function(){
				this.param.pageNumber = 1;
				findPageInfo();
			},
			toPage : function(num){
				if(this.param.pageNumber == num || num <= 0) return;
				this.param.pageNumber = num;
				findPageInfo();
			},
			actionBindClick : function(clickAction){
				console.log(clickAction);
				window[clickAction]();
			}
		},
		mounted : function(){
		}
	});
	findPageInfo();
	findAllRole();
	initActions(function(data){
		contentVue.actions = data;
	});
});


var findAllRole = function(){
	post(urlConfig.webuser.findRoles,{},function(data){
		contentVue.roles = data.data;
	},function(data){
	
	});
}

var findPageInfo = function(){
	post(urlConfig.webuser.list,contentVue.param,function(data){
		contentVue.page = data.data;
	},function(data){
		
	});
}

var query = function(){
	contentVue.param.pageNumber = 1;
	findPageInfo();
}

var saveInfo = function(){
	
	contentVue.editData = {
		edit : false,
		nickName : '',
		loginName : '',
		roleName : '',
		adminDesc : '',
		roleId : '',
		phone : ''
	};
	
	layer.open({
	  type: 1,
	  title:'新增用户',
	  shadeClose: false,
	  area: ['400px', '400px'],
	  content: $("#editModel"),
	  btn : ['保存','取消'],
	  yes : function(index,layero){
	  	post(urlConfig.webuser.save,contentVue.editData,function(data){
	  		layer.msg(data.msg,{icon:1});
	  		layer.close(index);
	  		query();
	  	},function(data){
	  		layer.alert(data.msg,{icon:2});
	  	});
	  },
	  no : function(index,layero){
	  	
	  }
	});
}


var updateInfo = function(){
	
	if(contentVue.page.selected == null || contentVue.page.selected == undefined){
		layer.msg("请选择需要操作的信息",{icon:7});
		return;
	}
	post(urlConfig.webuser.updateInfo,{id:contentVue.page.list[contentVue.page.selected].id},function(data){
		data.data.edit = true;
		contentVue.editData = data.data;
		
		layer.open({
		  type: 1,
		  title:'编辑用户',
		  shadeClose: false,
		  area: ['400px', '400px'],
		  content: $("#editModel"),
		  btn : ['保存','取消'],
		  yes : function(index,layero){
		  	post(urlConfig.webuser.update,contentVue.editData,function(data){
		  		layer.msg(data.msg,{icon:1});
		  		layer.close(index);
		  		query();
		  	},function(data){
		  		layer.alert(data.msg,{icon:2});
		  	});
		  },
		  no : function(index,layero){
		  	
		  }
		});
	},function(data){
		layer.alert(data.msg,{icon:2});
	});

}

var success = function(){
	if(contentVue.page.selected == null || contentVue.page.selected == undefined){
		layer.msg("请选择需要操作的信息",{icon:7});
		return;
	}
	//询问框
	layer.confirm('是否启用当前用户？', {
	  btn: ['是','否'] //按钮
	}, function(){
	  
	  post(urlConfig.webuser.success,{id:contentVue.page.list[contentVue.page.selected].id},function(data){
	  	layer.msg(data.msg,{icon:1});
	  	query();
	  },function(data){
	  	layer.alert(data.msg,{icon:2});
	  });
	  
	  
	}, function(){
		
	});
}

var deleteInfo = function(){
	if(contentVue.page.selected == null || contentVue.page.selected == undefined){
		layer.msg("请选择需要操作的信息",{icon:7});
		return;
	}
	//询问框
	layer.confirm('是否删除当前用户？', {
	  btn: ['是','否'] //按钮
	}, function(){
	  post(urlConfig.webuser.deleteInfo,{id:contentVue.page.list[contentVue.page.selected].id},function(data){
	  	layer.msg(data.msg,{icon:1});
	  	query();
	  },function(data){
	  	layer.alert(data.msg,{icon:2});
	  });
	  
	  
	}, function(){
		
	});
}

var blockInfo = function(){
	if(contentVue.page.selected == null || contentVue.page.selected == undefined){
		layer.msg("请选择需要操作的信息",{icon:7});
		return;
	}
	//询问框
	layer.confirm('是否停用当前用户？', {
	  btn: ['是','否'] //按钮
	}, function(){
	  post(urlConfig.webuser.block,{id:contentVue.page.list[contentVue.page.selected].id},function(data){
	  	layer.msg(data.msg,{icon:1});
	  	query();
	  },function(data){
	  	layer.alert(data.msg,{icon:2});
	  });
	  
	  
	}, function(){
		
	});
}
