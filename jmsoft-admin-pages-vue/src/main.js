import Vue from 'vue';
import App from './App.vue';


//引入公共的scss   注意：创建项目的时候必须用scss
import './assets/scss/basic.scss';   

//导入字体样式
import './assets/font-awesome-4.7.0/scss/font-awesome.scss';




//ElementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';


Vue.use(ElementUI);

//配置路由
import router from './router/router.js';

//vuex
import store from './vuex/store.js';

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})


