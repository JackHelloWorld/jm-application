import Vue from 'vue';

//请求数据
import VueResource from 'vue-resource';

import cacheUtils from './CacheUtils.js';
Vue.use(VueResource);

var getToken = function() {
	return cacheUtils.getToken();
}

HttpUtils.prototype = {
	paramPost: (url, params, success, error, errResult) => {
		var self = this.self;
		Vue.http.post(url, {}, {
			params: params,
			headers: {
				token: getToken()
			}
		}).then(response => {
			if(response.status != 200) {
				self.$notify.error({
					title: '系统提示',
					message: '网络错误,请稍后重试'
				});
			} else {
				if(response.body.status)
					success(response.body);
				else
					error(response.body);
			}
		}, response => {
			self.$notify.error({
				title: '系统提示',
				message: '网络错误,请稍后重试'
			});
			if(errResult) {
				errResult();
			}
		});
	},
	bodyPost: (url, params, success, error, errResult) => {
		var self = this.self;
		Vue.http.post(url, params, {
			headers: {
				token: getToken()
			}
		}).then(response => {
			if(response.status != 200) {
				self.$notify.error({
					title: '系统提示',
					message: '网络错误,请稍后重试'
				});
			} else {
				if(response.body.status)
					success(response.body);
				else
					error(response.body);
			}
		}, response => {
			self.$notify.error({
				title: '系统提示',
				message: '网络错误,请稍后重试'
			});
			if(errResult) {
				errResult();
			}
		});
	},
	bodyAndParamPost: (url, body, params, success, error, errResult) => {
		var self = this.self;
		Vue.http.post(url, body, {
			params: params,
			headers: {
				token: getToken()
			}
		}).then(response => {
			if(response.status != 200) {
				self.$notify.error({
					title: '系统提示',
					message: '网络错误,请稍后重试'
				});
			} else {
				if(response.body.status)
					success(response.body);
				else
					error(response.body);
			}
		}, response => {
			self.$notify.error({
				title: '系统提示',
				message: '网络错误,请稍后重试'
			});
			if(errResult) {
				errResult();
			}
		});
	},
};

function HttpUtils(self) {
	var _this = Object.create(HttpUtils.prototype);
	_this.self = self;
	return _this;
}

var httpUtils = {
	This : (self) => {
		return new HttpUtils(self);
	}
}

export default httpUtils;