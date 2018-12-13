<template>
	<div id="actions">
		<el-button v-for="action in actions" :type="action.style" :icon="action.icon" @click.native="actionclick(action.clickAction)" v-html="action.text"></el-button>
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