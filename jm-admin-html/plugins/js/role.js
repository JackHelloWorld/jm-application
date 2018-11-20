var contentVue;
$(function(){
	contentVue = new Vue({
		el : '#page-content',
		data : {
			param : {
				pageSize : 10,
				pageNumber : 1,
				name : '',
				remark : '',
			},
			page : {
				list : [],
				navigatepageNums : [],
			},
			nodata : {
				colspan : 6,
				text : '无匹配的角色信息'
			},
			roles : [],
			editData : {
				name : '',
				remark : '',
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
	initActions(function(data){
		contentVue.actions = data;
	});
});


var findPageInfo = function(){
	post(urlConfig.role.list,contentVue.param,function(data){
		contentVue.page = data.data;
	},function(data){
		
	});
}

var query = function(){
	contentVue.param.pageNumber = 1;
	findPageInfo();
}

var addInfo = function(){
	
	contentVue.editData = {
		name : '',
		remark : ''
	};
	
	layer.open({
	  type: 1,
	  title:'新增角色',
	  shadeClose: false,
	  area: ['400px', '400px'],
	  content: $("#editModel"),
	  btn : ['保存','取消'],
	  yes : function(index,layero){
	  	post(urlConfig.role.save,contentVue.editData,function(data){
	  		layer.msg('新增成功',{icon:1});
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


var editInfo = function(){
	
	if(contentVue.page.selected == null || contentVue.page.selected == undefined){
		layer.msg("请选择需要操作的信息",{icon:7});
		return;
	}
	post(urlConfig.role.updateInfo,{id:contentVue.page.list[contentVue.page.selected].id},function(data){
		contentVue.editData = {
			name : data.data.name,
			id : data.data.id,
			remark : data.data.remark
		};
		
		layer.open({
		  type: 1,
		  title:'编辑角色',
		  shadeClose: false,
		  area: ['400px', '400px'],
		  content: $("#editModel"),
		  btn : ['保存','取消'],
		  yes : function(index,layero){
		  	post(urlConfig.role.update,contentVue.editData,function(data){
		  		layer.msg('修改成功',{icon:1});
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
	layer.confirm('是否启用当前角色？', {
	  btn: ['是','否'] //按钮
	}, function(){
	  
	  post(urlConfig.role.success,{id:contentVue.page.list[contentVue.page.selected].id},function(data){
	  	layer.msg('启用成功',{icon:1});
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
	layer.confirm('是否删除当前角色？', {
	  btn: ['是','否'] //按钮
	}, function(){
	  post(urlConfig.role.deleteInfo,{id:contentVue.page.list[contentVue.page.selected].id},function(data){
	  	layer.msg('删除成功',{icon:1});
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
	layer.confirm('是否停用当前角色？', {
	  btn: ['是','否'] //按钮
	}, function(){
	  post(urlConfig.role.block,{id:contentVue.page.list[contentVue.page.selected].id},function(data){
	  	layer.msg('停用成功',{icon:1});
	  	query();
	  },function(data){
	  	layer.alert(data.msg,{icon:2});
	  });
	  
	  
	}, function(){
		
	});
}

var zNodes;
var treeObj;

var settings = {
	data : {
		simpleData : {
			enable : true,
			
		},
		key : {
			title : 'text',
			name : 'text',
			children : 'nodes',
			url : null,
			click : 'null',
			checked: 'own',
		}
	},
	check: {
		enable: true,
		nocheck : false,
		autoCheckTrigger: false
	}
};
var resourceClick = function(){
	if(contentVue.page.selected == null || contentVue.page.selected == undefined){
		layer.msg("请选择需要操作的信息",{icon:7});
		return;
	}
	var id = contentVue.page.list[contentVue.page.selected].id;
	post(urlConfig.role.findResource,{id:id},function(data){
			zNodes = data.data;
			treeObj = $.fn.zTree.init($("#resourceUl"), settings, zNodes);
			treeObj.expandAll(true);
			//页面层
			layer.open({
			  type: 1,
			  title : '资源授权:',
			  skin: 'layui-layer-rim', //加上边框
			  area: ['400px', '80%'], //宽高
			  content: $("#resourceModel"),
			  btn:['确定','取消'],
			  yes : function(index){
			  	var nodes = treeObj.getCheckedNodes(true);
			  	var ids = '';
				for(var i = 0;i<nodes.length;i++){
					ids = ids + nodes[i].id + ',';
				}
			  	post(urlConfig.role.resource,{id:id,ids:ids},function(d){
			  		layer.msg("授权成功",{icon:1});
			  		layer.close(index);
			  	},function(d){
			  		layer.msg(d.msg,{icon:2});
			  	});
			  },
			});
		},function(data){
			layer.msg(data.msg,{icon:7});
	});
}
