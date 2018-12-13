var settings = {
	data: {
		simpleData: {
			enable: true,
		},
		key: {
			title: 'text',
			name: 'text',
			children: 'nodes',
			url: null,
			click: 'null'
		}
	},
	callback: {
		onClick: onClick
	}
};

var resourceVue;

function onClick(event, treeId, treeNode, clickFlag) {
	Vue.set(resourceVue.updateData, "action", null);
	Vue.set(resourceVue.updateData, 'edit', false);
	Vue.set(resourceVue.updateData, 'parentId', treeNode.parentId);
	Vue.set(resourceVue.updateData, 'text', treeNode.text);
	Vue.set(resourceVue.updateData, 'clickAction', treeNode.clickAction);
	Vue.set(resourceVue.updateData, 'id', treeNode.id);
	Vue.set(resourceVue.updateData, 'url', treeNode.url);
	Vue.set(resourceVue.updateData, 'icon', treeNode.icon);
	Vue.set(resourceVue.updateData, 'intro', treeNode.intro);
	Vue.set(resourceVue.updateData, 'sort', treeNode.sort);
	Vue.set(resourceVue.updateData, 'type', treeNode.type);
	Vue.set(resourceVue.updateData, 'style', treeNode.style);

};

var initData = function() {
	resourceVue = new Vue({
		el: '#page-content',
		data: {
			updateData: {
				edit: false,
				parentId: 0
			},
			actionList: []
		},
		methods: {
			methodBind: function(e) {
				var method = $(e.currentTarget).attr("click-key");
				resourceVue[method](e);
			},
			addInfo: function(e) {
				var parentId = resourceVue.updateData.id ? resourceVue.updateData.id : 0;
				resourceVue.updateData = {
					edit: true,
					parentId: parentId,
					action: 2
				};
			},
			editInfo: function(e) {
				if(!resourceVue.updateData.id) {
					layer.msg("请选择修改的资源", {
						icon: 7
					});
					return;
				}
				Vue.set(resourceVue.updateData, "edit", true);
				Vue.set(resourceVue.updateData, "action", 1);

			},
			saveInfo: function(e) {
				if(resourceVue.updateData.action != 1 && resourceVue.updateData.action != 2) {
					layer.msg("未选择操作", {
						icon: 7
					});
					return;
				}
				var url = "";
				if(resourceVue.updateData.action == 2) {
					url = urlConfig.resource.saveResource;
				} else {
					url = urlConfig.resource.updateResource;
				}
				post(url, resourceVue.updateData, function(data) {
					layer.msg("操作成功", {
						icon: 1
					});
					resourceVue.updateData = {
						edit: false
					};
					initTree();
				}, function(data) {
					layer.msg(data.msg, {
						icon: 2
					});
				});
			},
			deleteInfo: function(e) {
				if(!resourceVue.updateData.id) {
					layer.msg("请选择要删除的资源", {
						icon: 7
					});
					return;
				}
				layer.confirm('是否删除,将同时删除子节点？', {
					btn: ['删除', '取消'],
					title: '系统提示',
				}, function() {
					post(urlConfig.resource.deleteResource, {
						id: resourceVue.updateData.id
					}, function(data) {
						layer.msg("删除成功", {
							icon: 1
						});
						resourceVue.updateData = {
							edit: false
						};
						initTree();
					}, function(data) {
						layer.msg(data.msg, {
							icon: 2
						});
					});
				}, function() {});
			}
		},
		mounted: function() {
			initTree();
		}
	});
};

var initTree = function() {
	post(urlConfig.resource.findAllMenus, {}, function(d) {
		zNodes = d.data;
		$.fn.zTree.init($("#resourceUl"), settings, zNodes);
	}, function(d) {
		layer.msg(data.msg, {
			icon: 2
		});
	});
};

$(function() {
	initActions(function(data) {
		initData();
		resourceVue.actionList = data;
	});
});