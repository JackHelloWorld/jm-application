<template>
	<!-- 所有的内容要被根节点包含起来 -->
	<div id="role" v-loading="loading">
		<el-card class="box-card">
			<div slot="header" class="clearfix">
				<span>操作栏</span>
			</div>
			<el-row>
				<el-col :span="24">
					<el-form :inline="true" class="demo-form-inline">
						<el-form-item label="角色名称">
							<el-input placeholder="角色名称"></el-input>
						</el-form-item>
					</el-form>
				</el-col>
				<el-col :span="24">
					<Actions :This="this" />
				</el-col>
			</el-row>
		</el-card>

		<el-card class="box-card">
			<div slot="header" class="clearfix">
				<span>角色列表</span>
			</div>
			<div v-for="o in 4" :key="o" class="text item">
				{{'列表内容 ' + o }}
			</div>
		</el-card>

	</div>
</template>

<script>
	import Actions from './Actions.vue';

	export default {
		data() {
			return {
				loading : true
			}
		},
		components: {
			'Actions': Actions
		},
		mounted: function() {
			this.loading = false
		},
		methods: {
			query() {
				console.log("查询")
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>