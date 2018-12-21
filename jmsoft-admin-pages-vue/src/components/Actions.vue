<template>
	<div id="Actions">
		<el-button :key="action.text" v-for="action in this.actions" :type="action.style" @click.native="actionclick(action.clickAction)">
			<template>
				<i :class="action.icon"></i>
				<span v-html="action.text"></span>
			</template>
			
			
		</el-button>
	</div>
</template>

<script>
	
	import httpUtils from '../utils/httpUtils.js';
	import apiConfig from '../utils/ApiConfig.js';
	import vueEvent from '../utils/VueEvent.js';
	
	export default {
		data(){
			return {
				actions : []
			}
		},
		props : ['This'],
		mounted : function(){
			vueEvent.$on('actions',(data)=>{
				this.actions = data;
			});
		},
		methods : {
			actionclick(clickAttr){
				if(this.This){
					console.log(clickAttr);
					this.This[clickAttr]();
				}else{
					console.error("Missing attribute value:This")
				}
			}
		}
	}
	
</script>

<style>
</style>