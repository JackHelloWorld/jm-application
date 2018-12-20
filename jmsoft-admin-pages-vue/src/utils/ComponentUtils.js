
import { Loading } from 'element-ui';
import { Message } from 'element-ui';

export default {
	//加载框
	loading(msg){
		const loading = Loading.service({
		  lock: true,
		  text: msg,
		  spinner: 'el-icon-loading',
		  background: 'rgba(0, 0, 0, 0.3)'
		});
		return loading;
	},
	//消息框
	message : {
		success(msg){
			var m = Message({
			  showClose: true,
			  message: msg,
			  type: 'success'
			});
			return m;
		},
		warning(msg){
			var m = Message({
			  showClose: true,
			  message: msg,
			  type: 'warning'
			});
			return m;
		},
		error(msg){
			var m = Message({
			  showClose: true,
			  message: msg,
			  type: 'error'
			});
			return m;
		},
	},
}