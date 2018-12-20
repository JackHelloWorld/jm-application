<template>
	<div id="webuser" v-loading="loading">
		<el-card class="box-card">
			<div slot="header" class="clearfix">
				<span class="card-title">操作栏</span>
			</div>
			<el-row>
				<el-col :span="24">
					<el-form :inline="true" class="demo-form-inline">
						<el-form-item label="用户昵称">
							<el-input v-model="queryData.nickName" placeholder="用户昵称"></el-input>
						</el-form-item>
						<el-form-item label="登录名">
							<el-input v-model="queryData.loginName" placeholder="登录名"></el-input>
						</el-form-item>
						<el-form-item label="角色">
							<el-input v-model="queryData.roleName" placeholder="角色"></el-input>
						</el-form-item>
						<el-form-item label="电话">
							<el-input v-model="queryData.phone" placeholder="电话"></el-input>
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
				<span class="card-title">用户列表</span>
			</div>

			<div>

				<el-table :data="page.list" style="width: 100%" @current-change="handleCurrentChange" border>
					<el-table-column prop="id" label="用户名称">
						<template slot-scope="scope">
							<el-radio v-model="selectRow.id" :label="scope.row.id">{{ scope.row.nickName }}</el-radio>
						</template>
					</el-table-column>
					<el-table-column prop="loginName" label="登录名">
					</el-table-column>
					<el-table-column prop="roleName" label="角色">
					</el-table-column>
					<el-table-column prop="phone" label="电话">
					</el-table-column>
					<el-table-column prop="adminDesc" label="描述">
					</el-table-column>
					<el-table-column prop="createtime" label="创建时间">
					</el-table-column>
					<el-table-column prop="lastLoginTime" label="最后一次登录时间">
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
				<el-form-item label="登录名" prop="loginName" :rules="[{ required: true, message: '登录名不能为空'}]">
					<el-input :disabled="form.edit" v-model="form.loginName" placeholder="请输入登录名">
					</el-input>
				</el-form-item>
				<el-form-item label="用户名" prop="nickName" :rules="[{ required: true, message: '用户名不能为空'}]">
					<el-input v-model="form.nickName" placeholder="请输入用户名"></el-input>
				</el-form-item>
				<el-form-item label="电话">
					<el-input v-model="form.phone" placeholder="请输入电话"></el-input>
				</el-form-item>
				<el-form-item label="角色">
					<el-select v-model="form.roleId" placeholder="请选择" style="width: 100%;">
						<el-option
						  v-for="item in editModal.roles"
						  :key="item.id"
						  :label="item.name"
						  :value="item.id">
						</el-option>
					</el-select>
					
					
				</el-form-item>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button @click="editModal.show = false">取 消</el-button>
				<el-button type="primary" @click="formSubmit('form')">确 定</el-button>
			</span>
		</el-dialog>

		<!-- 授权窗口 -->
		<el-dialog title="用户授权" width="430px" :close-on-click-modal="false" :visible.sync="resourceModal.show" left>
			<el-row>
				<el-col :span="24">
					<el-tree ref="tree" :data="resourceModal.data" show-checkbox default-expand-all node-key="id" :default-checked-keys="resourceModal.selectData" :expand-on-click-node="false"
					 :highlight-current="true" :check-on-click-node="true" :check-strictly="resourceModal.strictly" :props="{'label':'text','children':'nodes'}">
					</el-tree>
				</el-col>
			</el-row>


			<span slot="footer" class="dialog-footer">
				<el-button @click="resourceModal.show = false">取 消</el-button>
				<el-button type="primary" @click="resourceGo()">确 定</el-button>
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
	import componentUtils from '../utils/ComponentUtils.js';

	export default {
		data() {
			return {
				loading: true,
				resourceModal: {
					show: false,
					data: [],
					strictly: true,
					selectData : [],
				},
				editModal: {
					show: false,
					title: '',
					type: 0, //类型:1:保存,2:修改
					loading: false,
					roles : []
				},
				page: {
					list: [],
					navigatepageNums: []
				},
				form: {
					edit : false,
					nickName : '',
					loginName : '',
					roleName : '',
					adminDesc : '',
					roleId : '',
					phone : ''
				},
				selectRow: {
					id: null
				},
				queryData: {
					nickName: '',
					loginName: '',
					roleName: '',
					phone: '',
					pageSize: 10,
					pageNumber: 1,
				}
			}
		},
		components: {
			'Actions': Actions,
			'Pagination': Pagination
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
					componentUtils.message.warning(msg);
					return;
				}
				action(this.selectRow);
			},
			findList() {
				const self = this;
				httpUtils.paramPost(apiConfig.webuser.list, this.queryData, (data) => {
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
			findResourceSelect(data){
				for(var i = 0;i<data.length;i++){
					if(data[i].own){
						this.resourceModal.selectData.push(data[i].id);
					}
					if(data[i].nodes && data[i].nodes.length > 0){
						this.findResourceSelect(data[i].nodes);
					}
				}
			},
			resourceGo(){
				//组装数据
				var keys = this.$refs.tree.getCheckedKeys();
				var halfKeys = this.$refs.tree.getHalfCheckedKeys();
				halfKeys.push(...keys);
				const loading = componentUtils.loading("授权中,请稍后...");
				httpUtils.paramPost(apiConfig.webuser.resource,{id:this.selectRow.id,ids:halfKeys.join(",")},(data)=>{
					loading.close();
					this.resourceModal.show = false;
					componentUtils.message.success("授权成功");
				},(err)=>{
					loading.close();
					componentUtils.message.error("授权成功");
				},function(){
					loading.close();
				});
			},
			saveInfo() {
				this.form = {
					edit : false,
					nickName : '',
					loginName : '',
					roleName : '',
					adminDesc : '',
					roleId : '',
					phone : ''
					
				};
				this.editModal.title = '新增用户';
				this.editModal.type = 1;
				this.editModal.show = true;
			},
			updateInfo() {
				this.isSelectItem((row) => {
					this.form = row;
					this.form.edit=true;
					delete this.form.createtime;
					this.editModal.title = '修改用户';
					this.editModal.type = 2;
					this.editModal.show = true;
				}, '请选择需要修改的信息');
			},
			success() {
				this.isSelectItem((row) => {

					this.$confirm('将启用选中用户, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {

						httpUtils.paramPost(apiConfig.webuser.success, {
							id: row.id
						}, (data) => {
							componentUtils.message.success('操作成功');
							this.query();
						}, (err) => {
							componentUtils.message.error(err.msg);
						});

					}).catch(() => {

					});

				}, '请选择需要启用的信息');
			},
			blockInfo() {
				this.isSelectItem((row) => {

					this.$confirm('将停用选中用户, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {

						httpUtils.paramPost(apiConfig.webuser.block, {
							id: row.id
						}, (data) => {
							this.query();
							componentUtils.message.success('操作成功');
						}, (err) => {
							componentUtils.message.error(err.msg);
						});

					}).catch(() => {

					});

				}, '请选择需要停用的信息');
			},
			deleteInfo() {
				this.isSelectItem((row) => {

					this.$confirm('将删除选中用户并且无法恢复, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						httpUtils.paramPost(apiConfig.webuser.deleteInfo, {
							id: row.id
						}, (data) => {
							this.query();
							componentUtils.message.success('操作成功');
						}, (err) => {
							componentUtils.message.error(err.msg);
						});

					}).catch(() => {

					});

				}, '请选择需要删除的信息');
			},
			resource() {
				this.isSelectItem((row) => {
					var loading = componentUtils.loading('加载授权中,请稍后...');
					this.resourceModal.data = [];
					this.resourceModal.selectData=[];
					this.resourceModal.strictly = true;
					httpUtils.paramPost(apiConfig.webuser.findResource, {
						id: row.id
					}, (data) => {
						loading.close();
						this.resourceModal.show = true;
						this.resourceModal.data = data.data;
						var self = this;
						this.findResourceSelect(this.resourceModal.data);
						setTimeout(function(){
							self.resourceModal.strictly = false;
						}, 2000);

					}, (err) => {
						loading.close();
					}, function() {
						loading.close();
					});
				}, '请选择需要授权的信息');
			},
			formSubmit(formName) {

				this.$refs[formName].validate((valid) => {
					if (valid) {
						var url = null;
						if (this.editModal.type == 1) {
							url = apiConfig.webuser.save;
						} else if (this.editModal.type == 2) {
							url = apiConfig.webuser.update;
						}

						if (this.editModal.loading)
							return;

						this.editModal.loading = true;

						const loading = componentUtils.loading('操作中,请稍后');

						var self = this;
						httpUtils.paramPost(url, this.form, (data) => {
							loading.close();
							this.editModal.loading = false;
							componentUtils.message.success('操作成功');
							this.query();
							this.editModal.show = false;
						}, (err) => {
							loading.close();
							componentUtils.message.error(err.msg);
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
		},
		mounted : function(){
			this.query();
			httpUtils.paramPost(apiConfig.webuser.findRoles,{},(data)=>{
				this.editModal.roles = data.data;
				this.loading = false;
			},(err)=>{
				this.loading = false;
			});
		}
	}
</script>

<style lang="scss" scoped>

</style>
