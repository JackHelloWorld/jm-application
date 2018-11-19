var config = {
	companyName : "后台管理系统",
	version : 'v1.0'
};

var urlConfig = {
	
	//登录
	login : 'http://192.168.2.101:9999/user/login',
	
	//获取系统分配session
	getsessionid : 'http://192.168.2.101:9999/user/login/code/initsession',
	
	//登录验证码
	loginCode : 'http://192.168.2.101:9999/user/login/code',
	//获得菜单资源信息
	getMenus : 'http://192.168.2.101:9999/home/find/menus',
	//搜索菜单
	searchMenus : 'http://192.168.2.101:9999/home/search/menus',
	//获得个人信息
	getPersonalInfo : 'http://192.168.2.101:9999/user/get/info',
	//退出登录
	userExit : 'http://192.168.2.101:9999/user/logout',
	//修改密码
	updatePassword : 'http://192.168.2.101:9999/user/update/password',
	
	//根据菜单获得功能资源信息
	getAction : 'http://192.168.2.101:9999/home/find/action',
	
	common : {
		
		//获得地址信息
		getAddress : 'http://192.168.2.101:9999/common/get/address',
		//图片上传地址
		imgUpload : 'http://192.168.2.101:9999/common/upload/img',
		//文件上传地址
		fileUpload : 'http://192.168.2.101:9999/common/upload/file',
		//修改头像
		updateProfile : 'http://192.168.2.101:9999/common/update_profile',
	},
	
	/**resource.html*/
	resource : {
		findAllMenus : 'http://192.168.2.101:9999/resource/find',
		saveResource : 'http://192.168.2.101:9999/resource/save',
		updateResource : 'http://192.168.2.101:9999/resource/update',
		deleteResource : 'http://192.168.2.101:9999/resource/delete',
	},
	
	/**dictionary.html*/
	dictionary : {
		findAllDictionary : 'http://192.168.2.101:9999/dictionary/find',
		saveDictionary : 'http://192.168.2.101:9999/dictionary/save',
		updateDictionary : 'http://192.168.2.101:9999/dictionary/update',
		deleteDictionary : 'http://192.168.2.101:9999/dictionary/delete',
	},
	
	/**role.html*/
	role : {
		list : 'http://192.168.2.101:9999/role/list',
		updateInfo : 'http://192.168.2.101:9999/role/update/info',
		update : 'http://192.168.2.101:9999/role/update',
		save : 'http://192.168.2.101:9999/role/save',
		deleteInfo : 'http://192.168.2.101:9999/role/delete',
		resource : 'http://192.168.2.101:9999/role/resource',
		findResource : 'http://192.168.2.101:9999/role/find/resource',
	},
	
	/**webuser.html*/
	webuser : {
		list : 'http://192.168.2.101:9999/webuser/list',
		updateInfo : 'http://192.168.2.101:9999/webuser/update/info',
		update : 'http://192.168.2.101:9999/webuser/update',
		save : 'http://192.168.2.101:9999/webuser/save',
		deleteInfo : 'http://192.168.2.101:9999/webuser/delete',
		findRoles : 'http://192.168.2.101:9999/webuser/find/roles',
		block : 'http://192.168.2.101:9999/webuser/block',
		success : 'http://192.168.2.101:9999/webuser/success',
	},
	
}
