import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);


/*1.state在vuex中用于存储数据*/
var state={
	
}

/*2.mutations里面放的是方法，方法主要用于改变state里面的数据
*/
var mutations={
	
}

/*3、优点类似计算属性   ，  改变state里面的count数据的时候会触发 getters里面的方法 获取新的值 (基本用不到)*/


var getters= {
	
}




var actions= {
	
}



//vuex  实例化 Vuex.store并注意
const store = new Vuex.Store({
    state,
    mutations,
    getters,
    actions
})


export default store;

