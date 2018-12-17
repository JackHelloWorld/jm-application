<template>
	<div id="role" v-loading="loading">
		<el-card class="box-card">
			<div slot="header" class="clearfix">
				<span class="card-title">操作栏</span>
			</div>
			<el-row>
				<el-col :span="24">
					<el-form :inline="true" class="demo-form-inline">
						<el-form-item label="角色名称">
							<el-input v-model="queryData.name" placeholder="角色名称"></el-input>
						</el-form-item>
						<el-form-item label="角色备注">
							<el-input v-model="queryData.remark" placeholder="角色备注"></el-input>
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
				<span class="card-title">角色列表</span>
			</div>

			<div>

				<el-table :data="page.list" style="width: 100%" @current-change="handleCurrentChange" border>
					<el-table-column prop="id" label="角色名称">
						<template slot-scope="scope">
							<el-radio v-model="selectId" :label="scope.row.id">{{ scope.row.name }}</el-radio>
						</template>
					</el-table-column>
					<el-table-column prop="remark" label="备注信息">
					</el-table-column>
					<el-table-column prop="createTime" label="创建时间">
					</el-table-column>
					<el-table-column prop="createUserName" label="创建人">
					</el-table-column>
					<el-table-column prop="status" label="状态">
						<template slot-scope="scope">
							<span v-if="scope.row.status == 0" type="success" disabled>正常</span>
							<span v-if="scope.row.status == 1" type="warning" disabled>已停用</span>
						</template>
					</el-table-column>
				</el-table>

			</div>
		</el-card>

	</div>
</template>

<script>
	import Actions from './Actions.vue';
	import httpUtils from '../utils/httpUtils.js';
	import apiConfig from '../utils/ApiConfig.js';
	import tools from '../utils/Tools.js';

	export default {
		data() {
			return {
				loading: true,
				page: {
					list: []
				},
				selectId: null,
				queryData: {
					name: '',
					remark: '',
					pageSize: 10,
					pageNumber: 1,
				}
			}
		},
		components: {
			'Actions': Actions
		},
		mounted: function() {
			this.loading = false;
			this.query();
		},
		methods: {
			handleCurrentChange(row) { //点击行选择
				if (row)
					this.selectId = row.id;
			},
			isSelectItem() { //检查是否已选择行
				return this.selectId != null && this.selectId != '';
			},
			findList() {
				const self = this;
				httpUtils.This(this).paramPost(apiConfig.role.list, this.queryData, (data) => {
					tools.pageUtils(this.page, data.data);
					this.loading = false;
				}, (err) => {
					self.$notify.error({
						title: '系统提示',
						message: err.msg
					});
				});

			},
			query() {
				this.queryData.pageNumber = 1;
				this.page.list = [];
				this.findList();
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
