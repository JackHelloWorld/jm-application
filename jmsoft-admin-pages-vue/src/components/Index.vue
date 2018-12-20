<template>
	<div id="index">
		<el-container v-loading.fullscreen.lock="fullscreenLoading">
			<el-aside :style="'width:'+viewData.leftWidth+'px;'">
				<el-row>
					<el-col class="logo" :span="24">
						<h2><i class="fa fa-flag"></i><span v-if="viewData.leftWidth >= 150">管理系统</span></h2></el-col>
					<el-col :span="24">
						<CommonLeftNav :menus="commonLeft.menus" style="overflow: hidden;" :collapse="viewData.collapse" />
					</el-col>
				</el-row>
			</el-aside>
			<el-container :style="'height:'+viewData.leftHeight+'px;'">
				<el-header style="height: auto;">
					<el-row>
						<el-col :span="4">
							<el-button type="primary" @click.native="toggleBar()" icon="fa fa-navicon"></el-button>
						</el-col>
						<el-col :span="20">
							<CommonHeader/>
						</el-col>

					</el-row>
				</el-header>
				<el-container>
					<el-main>
						<el-row style="background-color: #FFFFFF;margin-bottom: 10px;padding: 20px 5px;">
							<el-col :span="12">
								<h2 v-html="viewData.title"></h2></el-col>
							<el-col :span="12" style="text-align: right;">
								<div style="float: right;">
									<el-breadcrumb separator-class="el-icon-arrow-right">
										<el-breadcrumb-item @click.native="toHome()" style="font-weight: bold;">
											<a href="javascript:;">首页</a>
										</el-breadcrumb-item>
										<el-breadcrumb-item v-for="menu in viewData.menuBar">
											<i :class="menu.icon"></i>
											<span v-html="menu.text"></span>
										</el-breadcrumb-item>
									</el-breadcrumb>
								</div>
							</el-col>
						</el-row>
						<router-view style="padding: 5px;"></router-view>
					</el-main>
					<el-footer style="height: auto;">
						<CommonFooter/>
					</el-footer>
				</el-container>
			</el-container>
		</el-container>
	</div>

</template>

<script>
	
	import CommonLeftNav from './CommonLeftNav.vue';
	import CommonHeader from './CommonHeader.vue';
	import CommonFooter from './CommonFooter.vue';
	import httpUtils from '../utils/httpUtils.js';
	import apiConfig from '../utils/ApiConfig.js';
	import vueEvent from '../utils/VueEvent.js';
	import tools from '../utils/Tools.js';

	var self;

	export default {
		data() {
			return {
				viewData: {
					collapse: false,
					leftWidth: '',
					leftHeight: '',
					menuBar: [],
					title: '控制中心'
				},
				commonLeft: {
					menus: [],
					menusList: []
				},
				fullscreenLoading: true
			}
		},
		components: {
			'CommonLeftNav': CommonLeftNav,
			'CommonHeader': CommonHeader,
			'CommonFooter': CommonFooter
		},
		methods: {
			toHome() {
				this.$router.push("/index");
				vueEvent.$emit('defaultActive', '');
			},
			toggleBar(){
				this.viewData.collapse = !this.viewData.collapse;
				if(this.viewData.collapse)
					this.viewData.leftWidth = 64;
				else{
					var bodyWidth = document.body.clientWidth;
					var wid = bodyWidth * 0.15;
					if(wid <= 150) {
						self.viewData.leftWidth = 150;
					} else {
						self.viewData.leftWidth = wid;
					}
				}
			}
		},
		mounted: function() {
			self = this;
			calcSize();

			//加载菜单
			httpUtils.paramPost(apiConfig.getMenus, {}, (data) => {
				this.fullscreenLoading = false;
				//储存地址映射
				var list = [];
				initMenus(data.data, list);
				self.commonLeft.menus = data.data;
				self.commonLeft.menusList = list;
				var href = location.href.split("#");
				var url = href[href.length - 1].split("?")[0];
				var t = true;
				for(var i = 0; i < list.length; i++) {
					if(!tools.isNull(list[i].url) && url == ('/index' + list[i].url)) {
						t = false;
						vueEvent.$emit('defaultActive', list[i].index);
						break;
					}
				}
				//默认处理
				if(t) {
					vueEvent.$emit('defaultActive', '');
				}
			}, (err) => {
				this.fullscreenLoading = false;
				self.$notify.error({
					title: '请求错误',
					message: err.msg
				});
			},function(){
				self.fullscreenLoading = false;
			});

			vueEvent.$on('defaultActive', (data) => {
				var result = findMenuBar(this.commonLeft.menusList, data);
				this.viewData.menuBar = result.menuBar;
				this.viewData.title = result.title;
				if(this.$route.query.menuId) {
					//加载功能信息
					httpUtils.paramPost(apiConfig.getAction, {
						parent_id: this.$route.query.menuId
					}, (data) => {
						vueEvent.$emit('actions', data.data);
					}, (err) => {
						self.$notify.error({
							title: '系统提示',
							message: err.msg
						});
					});
				}

			});
		}
	}

	window.onresize = function() {
		calcSize();
	};

	var findMenuBar = function(menuList, index) {
		var list = [];
		var menu = null;
		for(var i = 0; i < menuList.length; i++) {
			if(menuList[i].index == index) {
				menu = menuList[i];
				break;
			}
		}

		if(!menu) return {
			menuBar: [{
				icon: '',
				text: '控制中心'
			}],
			title: '控制中心'
		};

		var result = [];
		findParentId(result, menuList, menu.parentId);
		result.push(menu);
		return {
			title: menu.text,
			menuBar: result
		}
	}

	var findParentId = function(result, all, parentId) {
		if(parentId == 0 || parentId == null) return;
		for(var i = 0; i < all.length; i++) {
			if(all[i].id == parentId) {
				result.unshift(all[i]);
				findParentId(result, all, all[i].parentId);
				break;
			}
		}
	}

	var calcSize = function() {
		
		if(!self){
			return;
		}
		
		var bodyWidth = document.body.clientWidth;
		var wid = bodyWidth * 0.15;
		self.viewData.leftWidth = wid;
		if(wid <= 150) {
			self.viewData.collapse = true;
			self.viewData.leftWidth = 64;
		} else {
			self.viewData.collapse = false;
		}
		self.viewData.leftHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
	};

	var initMenus = function(menus, list, parentIndex) {
		if(!parentIndex) parentIndex = '';
		for(var i = 0; i < menus.length; i++) {
			if(parentIndex.length > 0) {
				menus[i].index = parentIndex + '-' + (i + 1);
			} else {
				menus[i].index = i + 1 + "";
			}

			list.push(menus[i]);
			initMenus(menus[i].nodes, list, menus[i].index);
		}
	}
</script>

<style lang="scss" scoped>
	.el-header {
		background-color: #409EFF;
		color: #333;
		line-height: 50px;
		padding: 0px;
		padding-right: 20px;
	}
	
	.el-aside {
		background-color: #FFFFFF;
		color: #333;
		border-right: 1px solid #d2d6de;
	}
	
	.el-main {
		background-color: #E9EEF3;
		color: #333;
		padding: 0px;
	}
	
	.el-footer {
		border-top: 1px solid;
		line-height: 50px;
		border-top-color: #d2d6de;
		border-left-color: #d2d6de;
	}
	
	.logo {
		line-height: 44px;
		padding: 3px;
		background: #409EFF;
		color: #FFFFFF;
		padding-left: 15px;
	}
</style>