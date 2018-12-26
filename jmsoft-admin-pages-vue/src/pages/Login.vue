<template>
	<div class="login" @keydown="keyEvent($event)">
		<el-container class="conttainer">

			<el-header>
				<div class="logo">
					<img src="../assets/login_logo.png" style="height: 50px;" alt="">
				</div>
			</el-header>
			<el-container class="wrap" style="overflow: hidden">
				<el-main style="overflow: hidden">
					<img src="../assets/img2.png" alt="">

					<div class="dl" v-loading="loading">
						<div class="d1 c1 ">
							<span>后台管理系统</span>
						</div>
						<div class="d1 c2">
							<i class="icon iconfont icon-zhanghu"></i>
							<input type="text" placeholder="请输入用户名" v-model="loginInfo.login_name">
						</div>
						<div class="d1 c3">
							<i class="icon iconfont icon-mima"></i>
							<input type="password" placeholder="请输入密码" v-model="loginInfo.password">
						</div>
						<!-- <div class="d1 c4">
                        <span>注册账户</span>
                        <span>找回密码</span>
                    </div> -->
						<div class="d1 c5">
							<button @click="login()">登录</button>
						</div>
					</div>
				</el-main>
			</el-container>
			<div class="img2"></div>
		</el-container>
	</div>
</template>

<script>
	
//网络请求
import httpUtils from '../utils/httpUtils.js';
import apiConfig from '../utils/ApiConfig.js';
import cacheUtils from '../utils/CacheUtils.js';

	var login = {
		data() {
			return {
				loading: false,
				loginInfo: {
					login_name: '',
					password: ''
				}
			}
		},
		methods: {
			login() {
				const self = this;
				if(self.loading) {
					return;
				}
				self.loading = true;
				httpUtils.paramPost(apiConfig.login, self.loginInfo, (data) => {
					cacheUtils.setUserInfo(data.data);
					cacheUtils.setToken(data.data.token);
					self.$router.push("/index");
				}, (err) => {
					self.$notify.error({
						title: '登录错误',
						message: err.msg
					});
					self.loading = false;
				}, function() {
					self.loading = false;
				});
			},
			keyEvent(event) {
				if(event.keyCode == 13 && !this.loading) {
					this.login();
				}
			}
		}

	}

	export default login;
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped="scoped">
	@import '../assets/scss/login.scss';
</style>