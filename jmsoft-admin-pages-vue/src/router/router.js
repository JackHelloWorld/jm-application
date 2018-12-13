import Vue from 'vue';

//配置路由
import VueRouter from 'vue-router';
Vue.use(VueRouter);

//1.创建组件
import Home from '../components/Home.vue';
import Index from '../components/Index.vue';
import Login from '../components/Login.vue';
import User from '../components/User.vue';
import Role from '../components/Role.vue';

import store from '../vuex/store.js';

//2.配置路由   注意：名字

const routes = [{
		path: '/index',
		component: Index,
		meta: {requireAuth: true},
		children : [
			{path: '',component: Home},
			{path: 'user',component: User},
			{path: 'role',component: Role},
		],
	},
	{
		path: '/login',
		component: Login
	},
	{
		path: '*',
		redirect: '/login'
	} /*默认跳转路由*/
];

//3.实例化VueRouter  注意：名字

const router = new VueRouter({
	routes // （缩写）相当于 routes: routes
});

import cacheUtils from '../utils/CacheUtils.js';

router.beforeEach((to, from, next) => {
	
	if(to.matched.some(res => res.meta.requireAuth)) { // 判断是否需要登录权限
		var token = cacheUtils.getToken();
		if(token != null) { // 判断是否登录
			next();
		} else { // 没登录则跳转到登录界面
			next({
				path: '/login',
			})
		}
	} else {
		next()
	}
});

export default router;