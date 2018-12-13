
export default {
	
	getUserInfo(){
		var userInfo = localStorage.getItem("userInfo");
		if(userInfo == null || userInfo == '' || userInfo == 'null') return null;
		return eval('('+userInfo+')');
	},
	setUserInfo(userInfo){
		localStorage.setItem('userInfo',JSON.stringify(userInfo));
	},
	setToken(token){
		localStorage.setItem('token',token);
	},
	getToken(){
		var token = localStorage.getItem("token");
		if(token == '' || token == null || token=='null') return null;
		return token;
	}
	
}

