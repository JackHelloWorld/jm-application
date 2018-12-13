<template>

	<div id="CommonLeftNav">
		<el-menu :default-active="defaultActive" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="collapse">
			<NavMenus :navMenus="menus" parentIndex=""></NavMenus>
		</el-menu>
	</div>

</template>

<script>
	
	import Nav from './Nav.vue';
	import vueEvent from '../utils/VueEvent.js';
	
	export default {
		data() {
			return {
				defaultActive : ''
			}
		},
		name : 'CommonLeftNav',
		props: ['menus','collapse'],
		methods: {
			handleOpen(key, keyPath) {
				
			},
			handleClose(key, keyPath) {
				
			}
		},
		mounted(){
			vueEvent.$on('defaultActive',(data) => {
				this.defaultActive = data;
			});
		},
		components : {
			'NavMenus' : Nav,
		}
	}
</script>

<style>

</style>