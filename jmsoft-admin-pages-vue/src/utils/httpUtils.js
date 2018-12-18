import Vue from 'vue';

//请求数据
import VueResource from 'vue-resource';

//通知
import { Notification } from 'element-ui';

import cacheUtils from './CacheUtils.js';
Vue.use(VueResource);

var getToken = function() {
	return cacheUtils.getToken();
}

var HttpUtils = {
	paramPost: (url, params, success, error, errResult) => {
		Vue.http.post(url, {}, {
			params: params,
			headers: {
				token: getToken()
			}
		}).then(response => {
			if(response.status != 200) {
				Notification.error({
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
			Notification.error({
				title: '系统提示',
				message: '网络错误,请稍后重试'
			});
			if(errResult) {
				errResult();
			}
		});
	},
	bodyPost: (url, params, success, error, errResult) => {
		Vue.http.post(url, params, {
			headers: {
				token: getToken()
			}
		}).then(response => {
			if(response.status != 200) {
				Notification.error({
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
			Notification.error({
				title: '系统提示',
				message: '网络错误,请稍后重试'
			});
			if(errResult) {
				errResult();
			}
		});
	},
	bodyAndParamPost: (url, body, params, success, error, errResult) => {
		Vue.http.post(url, body, {
			params: params,
			headers: {
				token: getToken()
			}
		}).then(response => {
			if(response.status != 200) {
				Notification.error({
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
			Notification.error({
				title: '系统提示',
				message: '网络错误,请稍后重试'
			});
			if(errResult) {
				errResult();
			}
		});
	},
};


export default HttpUtils;