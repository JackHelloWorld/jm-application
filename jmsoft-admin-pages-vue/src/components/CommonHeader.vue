<template>

	<div id="commonHeader">

		<el-row type="flex">
			<el-col :span="24" justify="end" style="text-align: right;color: #FFFFFF;">
				<span>欢迎您:<span v-html="userInfo.nickName"></span>!</span>&nbsp;&nbsp;
				<el-button type="primary">个人中心</el-button>
				<el-button type="primary" @click.native="logout()">退出</el-button>
			</el-col>
		</el-row>

	</div>

</template>

<script>
	
import cacheUtils from '../utils/CacheUtils.js';
	
export default {
	data() {
		return {
			userInfo : {
				nickName : '',
			}
		};
	},
	methods: {
		logout(){
			this.$router.push("/");
		}
	},
	mounted : function(){
		this.userInfo = cacheUtils.getUserInfo();
	}
}
</script>