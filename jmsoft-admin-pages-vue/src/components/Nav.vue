<template>
	<div id="nav">
		<label v-for="(menu,i) in navMenus">
			<el-submenu :index="menu.index" v-if="menu.nodes != null && menu.nodes.length > 0">
				<template slot="title">
					<i :class="menu.icon"></i>
					<span v-html="menu.text"></span>
				</template>
				<NavTemp :navMenus="menu.nodes"/>
			</el-submenu>
			<el-menu-item :index="menu.index" :route="menu.url" v-else  @click.native="clickRouter(menu.url,menu.id,menu.index)">
				<i :class="menu.icon"></i>
				<span slot="title" v-html="menu.text"></span>
			</el-menu-item>
		</label>

	</div>

</template>

<script>

	import vueEvent from '../utils/VueEvent.js';

	export default {
		name: 'NavTemp',
		data() {
			return {

			}
		},
		props: ['navMenus'],
		mounted: function() {
			
		},
		methods: {
			clickRouter(url, id, index) {
				this.$router.push({
					path: '/index' + url,
					query: {
						menuId: id
					}
				});
				vueEvent.$emit('defaultActive',index);
			}
		}
	}
</script>

<style>

</style>