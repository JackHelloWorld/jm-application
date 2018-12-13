var config = {
	companyName : "后台管理系统",
	version : 'v1.0'
};

var urlConfig = {
	
	//登录
	login : 'http://127.0.0.1:9999/user/login',
	
	//获取系统分配session
	getsessionid : 'http://127.0.0.1:9999/user/login/code/initsession',
	
	//登录验证码
	loginCode : 'http://127.0.0.1:9999/user/login/code',
	//获得菜单资源信息
	getMenus : 'http://127.0.0.1:9999/home/find/menus',
	//搜索菜单
	searchMenus : 'http://127.0.0.1:9999/home/search/menus',
	//获得个人信息
	getPersonalInfo : 'http://127.0.0.1:9999/user/get/info',
	//退出登录
	userExit : 'http://127.0.0.1:9999/user/logout',
	//修改密码
	updatePassword : 'http://127.0.0.1:9999/user/update/password',
	//修改资料
	updateProfile : 'http://127.0.0.1:9999/user/update/profile',
	
	//统计登录信息
	countLoginInfo : 'http://127.0.0.1:9999/user/count/logininfo',
	
	//获取登录信息
	findLoginInfo : 'http://127.0.0.1:9999/user/find/logininfo',
	
	//根据菜单获得功能资源信息
	getAction : 'http://127.0.0.1:9999/home/find/action',
	
	common : {
		
		//获得地址信息
		getAddress : 'http://127.0.0.1:9999/common/get/address',
		//图片上传地址
		imgUpload : 'http://127.0.0.1:9999/common/upload/img',
		//文件上传地址
		fileUpload : 'http://127.0.0.1:9999/common/upload/file',
		//修改头像
		updateImgProfile : 'http://127.0.0.1:9999/common/update_profile',
	},
	
	/**resource.html*/
	resource : {
		findAllMenus : 'http://127.0.0.1:9999/resource/find',
		saveResource : 'http://127.0.0.1:9999/resource/save',
		updateResource : 'http://127.0.0.1:9999/resource/update',
		deleteResource : 'http://127.0.0.1:9999/resource/delete',
	},
	
	/**dictionary.html*/
	dictionary : {
		findAllDictionary : 'http://127.0.0.1:9999/dictionary/find',
		saveDictionary : 'http://127.0.0.1:9999/dictionary/save',
		updateDictionary : 'http://127.0.0.1:9999/dictionary/update',
		deleteDictionary : 'http://127.0.0.1:9999/dictionary/delete',
	},
	
	/**role.html*/
	role : {
		list : 'http://127.0.0.1:9999/role/list',
		updateInfo : 'http://127.0.0.1:9999/role/update/info',
		update : 'http://127.0.0.1:9999/role/update',
		save : 'http://127.0.0.1:9999/role/save',
		deleteInfo : 'http://127.0.0.1:9999/role/delete',
		resource : 'http://127.0.0.1:9999/role/resource',
		findResource : 'http://127.0.0.1:9999/role/find/resource',
		block : 'http://127.0.0.1:9999/role/block',
		success : 'http://127.0.0.1:9999/role/success',
	},
	
	/**webuser.html*/
	webuser : {
		list : 'http://127.0.0.1:9999/webuser/list',
		resource : 'http://127.0.0.1:9999/webuser/resource',
		updateInfo : 'http://127.0.0.1:9999/webuser/update/info',
		update : 'http://127.0.0.1:9999/webuser/update',
		findResource : 'http://127.0.0.1:9999/webuser/find/resource',
		save : 'http://127.0.0.1:9999/webuser/save',
		deleteInfo : 'http://127.0.0.1:9999/webuser/delete',
		findRoles : 'http://127.0.0.1:9999/webuser/find/roles',
		block : 'http://127.0.0.1:9999/webuser/block',
		success : 'http://127.0.0.1:9999/webuser/success',
	},
	
}