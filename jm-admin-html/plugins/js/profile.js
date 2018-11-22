var contentVue;
$(function() {
	contentVue = new Vue({
		el: '#page-content',
		data: {
			showTag: 'loginrecord',
			userInfo : userInfo,
			loginData : {
				page : {
					list : [],
					hasNextPage : false,
				},
				loginCount : '加载中',
				param : {
					pageNumber : 1,
					pageSize : 10
				}
			},
			subParam : {
				update_password : {
					old_password : '',
					new_password : '',
					new_password1 : '',
					err_msg : ''
				},
				profile : {
					nickName : userInfo.nickName,
					address : userInfo.address,
					phone : userInfo.phone,
					intro : userInfo.intro,
					err_msg : ''
				},
			}
		},
		methods: {
			showTagClick: function(tag) {
				this.showTag = tag;
			},
			nextLoginLogPage : function(){
				if(contentVue.loginData.page.hasNextPage){
					this.loginData.param.pageNumber = this.loginData.param.pageNumber + 1;
					findLoginInfo();
				}
			},
			updatePwdAction : function(){
				if(this.subParam.update_password.old_password == null || $.trim(this.subParam.update_password.old_password).length == 0){
					this.subParam.update_password.err_msg = '请输入原密码';
					return;
				}
				if(this.subParam.update_password.new_password == null || $.trim(this.subParam.update_password.new_password).length == 0){
					this.subParam.update_password.err_msg = "请输入新密码";
					return;
				}
				if(this.subParam.update_password.new_password1 != this.subParam.update_password.new_password){
					this.subParam.update_password.err_msg = "确认密码输入有误";
					return;
				}
				this.subParam.update_password.err_msg = "";
				post(urlConfig.updatePassword,contentVue.subParam.update_password,function(data){
					layer.msg(data.msg,{icon:1});
				},function(data){
					contentVue.subParam.update_password.err_msg = data.msg;
				});
			},
			profileAction : function(){
				if(this.subParam.profile.nickName == null || $.trim(this.subParam.profile.nickName).length == 0){
					this.subParam.profile.err_msg = '请输入名称';
					return;
				}
				if(this.subParam.profile.address == null || $.trim(this.subParam.profile.address).length == 0){
					this.subParam.profile.err_msg = '请输入联系地址';
					return;
				}
				if(this.subParam.profile.phone == null || $.trim(this.subParam.profile.phone).length == 0){
					this.subParam.profile.err_msg = '请输入联系电话';
					return;
				}
				this.subParam.profile.err_msg = '';
				post(urlConfig.updateProfile,contentVue.subParam.profile,function(data){
					layer.msg(data.msg,{icon:1});
					
					//加载修改信息
					userInfo.nickName = contentVue.subParam.profile.nickName;
					userInfo.address = contentVue.subParam.profile.address;
					userInfo.phone = contentVue.subParam.profile.phone;
					userInfo.intro = contentVue.subParam.profile.intro;
					navContentVue.userInfo = userInfo;
					navContent2.userInfo = userInfo;
					
					setData("login_user",JSON.stringify(userInfo));
					
				},function(data){
					contentVue.subParam.profile.err_msg = data.msg;
				});
			}
		},
		mounted: function() {
			countLoginInfo();
		}
	});
	findLoginInfo();
});

//统计登录信息
var countLoginInfo = function(){
	post(urlConfig.countLoginInfo,{},function(data){
		contentVue.loginData.loginCount = data.data;
	},function(data){
		
	});
};


var findLoginInfo = function(){
	post(urlConfig.findLoginInfo,contentVue.loginData.param,function(data){
		contentVue.loginData.page.hasNextPage = data.data.hasNextPage;
		if(data.data.list.length > 0){
			for(var i = 0;i<data.data.list.length;i++){
				contentVue.loginData.page.list.push(data.data.list[i]);
			}
		}
	},function(data){
		
	});
}
