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

var dictionary;

function onClick(event, treeId, treeNode, clickFlag) {
	Vue.set(dictionaryVue.updateData, "action", null);
	Vue.set(dictionaryVue.updateData, 'edit', false);
	Vue.set(dictionaryVue.updateData, 'parentToken', treeNode.parentToken);
	Vue.set(dictionaryVue.updateData, 'text', treeNode.text);
	Vue.set(dictionaryVue.updateData, 'id', treeNode.id);
	Vue.set(dictionaryVue.updateData, 'sort', treeNode.sort);
	Vue.set(dictionaryVue.updateData, 'enText', treeNode.enText);
	Vue.set(dictionaryVue.updateData, 'token', treeNode.token);
	Vue.set(dictionaryVue.updateData, 'value', treeNode.value);

};

var initData = function() {
	dictionaryVue = new Vue({
		el: '#page-content',
		data: {
			updateData: {
				edit: false,
				parentToken: 0
			},
			actionList: []
		},
		methods: {
			methodBind: function(e) {
				var method = $(e.currentTarget).attr("click-key");
				dictionaryVue[method](e);
			},
			addInfo: function(e) {
				var parentToken = dictionaryVue.updateData.token ? dictionaryVue.updateData.token : '0';
				dictionaryVue.updateData = {
					edit: true,
					parentToken: parentToken,
					action: 2
				};
			},
			editInfo: function(e) {
				if(!dictionaryVue.updateData.id) {
					layer.msg("请选择修改的字典", {
						icon: 7
					});
					return;
				}
				Vue.set(dictionaryVue.updateData, "edit", true);
				Vue.set(dictionaryVue.updateData, "action", 1);
			},
			saveInfo: function(e) {
				if(dictionaryVue.updateData.action != 1 && dictionaryVue.updateData.action != 2) {
					layer.msg("未选择操作", {
						icon: 7
					});
					return;
				}
				var url = "";
				if(dictionaryVue.updateData.action == 2) {
					url = urlConfig.dictionary.saveDictionary;
				} else {
					url = urlConfig.dictionary.updateDictionary;
				}
				post(url, dictionaryVue.updateData, function(data) {
					layer.msg("操作成功", {
						icon: 1
					});
					dictionaryVue.updateData = {
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
				if(!dictionaryVue.updateData.id) {
					layer.msg("请选择要删除的字典", {
						icon: 7
					});
					return;
				}
				layer.confirm('是否删除,将同时删除子节点？', {
					btn: ['删除', '取消'],
					title: '系统提示',
				}, function() {
					post(urlConfig.dictionary.deleteDictionary, {
						id: dictionaryVue.updateData.id
					}, function(data) {
						layer.msg("删除成功", {
							icon: 1
						});
						dictionaryVue.updateData = {
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
	post(urlConfig.dictionary.findAllDictionary, {}, function(d) {
		zNodes = d.data;
		$.fn.zTree.init($("#dictionaryUl"), settings, zNodes);
	}, function(d) {
		layer.msg(data.msg, {
			icon: 2
		});
	});
};

$(function() {
	initActions(function(data) {
		initData();
		dictionaryVue.actionList = data;
	});
});