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
							<el-radio v-model="selectRow.id" :label="scope.row.id">{{ scope.row.name }}</el-radio>
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
				<Pagination :page="page" queryMethod="pageQuery" :This="this" :queryData="queryData" />
			</div>
		</el-card>


		<!-- 编辑窗口 -->
		<el-dialog :title="editModal.title" :close-on-click-modal="false" :visible.sync="editModal.show" left>
			<el-form ref="form" :model="form" label-width="80px">
				<el-form-item label="角色名称" prop="name" :rules="[{ required: true, message: '角色名称不能为空'}]">
					<el-input v-model="form.name" placeholder="请输入角色名称">
					</el-input>
				</el-form-item>
				<el-form-item label="角色描述" prop="remark" :rules="[{ required: true, message: '角色描述不能为空'}]">
					<el-input v-model="form.remark" placeholder="请输入角色描述"></el-input>
				</el-form-item>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button @click="editModal.show = false">取 消</el-button>
				<el-button type="primary" @click="formSubmit('form')">确 定</el-button>
			</span>
		</el-dialog>

	</div>
</template>

<script>
	import Actions from './Actions.vue';
	import httpUtils from '../utils/httpUtils.js';
	import apiConfig from '../utils/ApiConfig.js';
	import tools from '../utils/Tools.js';
	import Pagination from './Pagination.vue';

	export default {
		data() {
			return {
				loading: true,
				editModal: {
					show: false,
					title: '',
					type: 0, //类型:1:保存,2:修改
					loading: false
				},
				page: {
					list: [],
					navigatepageNums: []
				},
				form: {
					remark: '',
					name: '',
				},
				selectRow: {
					id: null
				},
				queryData: {
					name: '',
					remark: '',
					pageSize: 10,
					pageNumber: 1,
				}
			}
		},
		components: {
			'Actions': Actions,
			'Pagination': Pagination
		},
		mounted: function() {
			this.loading = false;
			this.query();
		},
		methods: {
			handleCurrentChange(row) { //点击行选择
				if (row)
					this.selectRow = row;
			},
			isSelectItem(action, msg) { //检查是否已选择行
				if (!msg) msg = '请选择需要操作的项';
				var t = this.selectRow != null && this.selectRow.id;
				if (!t) {
					this.$message({
						message: msg,
						type: 'warning'
					});
					return;
				}
				action(this.selectRow);
			},
			findList() {
				const self = this;
				httpUtils.paramPost(apiConfig.role.list, this.queryData, (data) => {
					tools.pageUtils(this.page, data.data);
					this.loading = false;
				}, (err) => {
					self.$notify.error({
						title: '系统提示',
						message: err.msg
					});
				});

			},
			pageQuery() {
				this.page.list = [];
				this.findList();
			},
			query() {
				this.queryData.pageNumber = 1;
				this.page.list = [];
				this.findList();
			},
			addInfo() {
				this.form = {
					remark: '',
					name: '',
				};
				this.editModal.title = '新增角色';
				this.editModal.type = 1;
				this.editModal.show = true;
			},
			editInfo() {
				this.isSelectItem((row) => {
						this.form = row;
						delete this.form.createTime;
						this.editModal.title = '修改角色';
						this.editModal.type = 2;
						this.editModal.show = true;
				},'请选择需要修改的信息');
			},
			success() {
				this.isSelectItem((row) => {
						
				this.$confirm('将启用选中角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
					
					httpUtils.paramPost(apiConfig.role.success,{id:row.id},(data)=>{
						this.$message({
							message: '操作成功',
							type: 'success'
						});
						this.query();
					},(err)=>{
						this.$message.error(err.msg);
					});
					
				}).catch(() => {
					
				});
					
				},'请选择需要启用的信息');
			},	
			blockInfo() {
				this.isSelectItem((row) => {
						
				this.$confirm('将停用选中角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
					
					httpUtils.paramPost(apiConfig.role.block,{id:row.id},(data)=>{
						this.query();
						this.$message({
							message: '操作成功',
							type: 'success'
						});
					},(err)=>{
						this.$message.error(err.msg);
					});
					
				}).catch(() => {
					
				});
					
				},'请选择需要停用的信息');
			},
			deleteInfo() {
				this.isSelectItem((row) => {
						
				this.$confirm('将删除选中角色并且无法恢复, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
					
					httpUtils.paramPost(apiConfig.role.deleteInfo,{id:row.id},(data)=>{
						this.query();
						this.$message({
							message: '操作成功',
							type: 'success'
						});
					},(err)=>{
						this.$message.error(err.msg);
					});
					
				}).catch(() => {
					
				});
					
				},'请选择需要删除的信息');
			},
			resourceClick() {
				this.isSelectItem((row) => {
					
					
					
					
				},'请选择需要授权的信息');
			},
			formSubmit(formName) {

				this.$refs[formName].validate((valid) => {
					if (valid) {
						var url = null;
						if (this.editModal.type == 1) {
							url = apiConfig.role.save;
						} else if (this.editModal.type == 2) {
							url = apiConfig.role.update;
						}

						if (this.editModal.loading)
							return;

						this.editModal.loading = true;

						const loading = this.$loading({
							lock: true,
							text: '操作中,请稍后',
							spinner: 'el-icon-loading',
							background: 'rgba(0, 0, 0, 0.3)'
						});

						var self = this;
						httpUtils.paramPost(url, this.form, (data) => {
							loading.close();
							this.editModal.loading = false;
							this.$message({
								message: '操作成功',
								type: 'success'
							});
							this.query();
							this.editModal.show = false;
						}, (err) => {
							loading.close();
							this.$message.error(err.msg);
							self.editModal.loading = false;
						}, function() {
							loading.close();
							self.editModal.loading = false;
						});
					} else {
						return false;
					}
				});
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
