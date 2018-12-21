<template>
	<div id="loginUser" v-loading="loading">
		<el-card class="box-card">
			<div slot="header" class="clearfix">
				<span class="card-title">操作栏</span>
			</div>
			<el-row>
				<el-col :span="24">
					<el-form :inline="true" class="demo-form-inline">
						<el-form-item label="昵称">
							<el-input v-model="queryData.nickName" placeholder="昵称"></el-input>
						</el-form-item>
						<el-form-item label="地址">
							<el-input v-model="queryData.address" placeholder="地址"></el-input>
						</el-form-item>
						<el-form-item label="名称">
							<el-input v-model="queryData.name" placeholder="真实名称"></el-input>
						</el-form-item>
						<el-form-item label="手机">
							<el-input v-model="queryData.phone" placeholder="手机号"></el-input>
						</el-form-item>
						<el-form-item label="证件">
							<el-input v-model="queryData.certificateNo" placeholder="证件号"></el-input>
						</el-form-item>
						<el-form-item label="状态">
							<el-select v-model="queryData.status" placeholder="请选择状态">
								<el-option label="正常" :value="0"></el-option>
								<el-option label="已禁用" :value="1"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="性别">
							<el-select v-model="queryData.sex" placeholder="请选择性别">
								<el-option :label="item.text" v-for="item in selectData.sexData" :key="item.text" :value="item.value"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="证件类型">
							<el-select v-model="queryData.certificateType" placeholder="请选择证件类型">
								<el-option :label="item.text" v-for="item in selectData.certificateTypeData" :key="item.text" :value="item.value"></el-option>
							</el-select>
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
					<el-table-column prop="id" label="真实名称">
						<template slot-scope="scope">
							<el-radio v-model="selectRow.id" :label="scope.row.id">{{ scope.row.name }}</el-radio>
						</template>
					</el-table-column>
					<el-table-column prop="profileImg" label="头像">
						<template slot-scope="scope">
							<img :src="scope.row.profileImg" style="height: 50px;" />
						</template>
					</el-table-column>
					<el-table-column prop="nickName" label="昵称">
					</el-table-column>
					<el-table-column prop="phone" label="手机">
					</el-table-column>
					<el-table-column prop="sexStr" label="性别">
					</el-table-column>
					<el-table-column prop="certificateTypeStr" label="证件类型">
					</el-table-column>
					<el-table-column prop="certificateNo" label="证件号">
					</el-table-column>
					<el-table-column prop="address" label="地址">
					</el-table-column>
					<el-table-column prop="createTime" label="创建时间">
					</el-table-column>
						<el-table-column prop="updateTime" label="资料更新时间">
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
				<el-form-item label="昵称" prop="nickName" :rules="[{ required: true, message: '昵称不能为空'}]">
					<el-input v-model="form.nickName" placeholder="请输入昵称">
					</el-input>
				</el-form-item>
				<el-form-item label="地址" prop="address" :rules="[{ required: true, message: '地址不能为空'}]">
					<el-input v-model="form.address" placeholder="请输入地址"></el-input>
				</el-form-item>
				<el-form-item label="真实名称" prop="name" :rules="[{ required: true, message: '真实名称不能为空'}]">
					<el-input v-model="form.name" placeholder="请输入真实名称"></el-input>
				</el-form-item>
				<el-form-item label="电话" prop="phone" :rules="[{ required: true, message: '电话不能为空'}]">
					<el-input v-model="form.phone" placeholder="请输入电话"></el-input>
				</el-form-item>
				<el-form-item label="性别" :rules="[{ required: true, message: '请选择性别'}]" prop="sex">
					<el-select v-model="form.sex" style="width: 100%;" placeholder="请选择性别">
						<el-option :label="item.text" v-for="item in selectData.sexData" :key="item.text" :value="item.value"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="证件类型" :rules="[{ required: true, message: '请选择证件类型'}]" prop="certificateType">
					<el-select v-model="form.certificateType" style="width: 100%;" placeholder="请选择证件类型">
						<el-option :label="item.text" v-for="item in selectData.certificateTypeData" :key="item.text" :value="item.value"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="证件号" prop="certificateNo" :rules="[{ required: true, message: '请输入证件号'}]">
					<el-input v-model="form.certificateNo" placeholder="请输入证件号"></el-input>
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
	import Actions from '../components/Actions.vue';
	import httpUtils from '../utils/httpUtils.js';
	import apiConfig from '../utils/ApiConfig.js';
	import tools from '../utils/Tools.js';
	import Pagination from '../components/Pagination.vue';
	import componentUtils from '../utils/ComponentUtils.js';

	export default {
		data() {
			return {
				loading: true,
				selectData: {
					sexData: [],
					certificateTypeData: []
				},
				editModal: {
					show: false,
					title: '',
					loading: false
				},
				page: {
					list: [],
					navigatepageNums: []
				},
				form: {
					nickName: '',
					address: '',
					name: '',
					sex: '',
					phone: '',
					certificateType: '',
					certificateNo: '',
				},
				selectRow: {
					id: null
				},
				queryData: {
					nickName: '',
					address: '',
					name: '',
					sex: '',
					phone: '',
					certificateType: '',
					certificateNo: '',
					status: '',
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
			this.query();
			httpUtils.paramPost(apiConfig.common.findDic, {
				'parentToken': 'DX001'
			}, (data) => {
				this.selectData.sexData = data.data;
			}, (err) => {
				componentUtils.message.error(err.msg);
			});
			httpUtils.paramPost(apiConfig.common.findDic, {
				'parentToken': 'DX002'
			}, (data) => {
				this.selectData.certificateTypeData = data.data;
			}, (err) => {
				componentUtils.message.error(err.msg);
			});

			setTimeout(function() {
				this.loading = false;
			}, 2000);
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
				httpUtils.paramPost(apiConfig.loginUser.list, this.queryData, (data) => {
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
			updateInfo() {
				this.isSelectItem((row) => {
					this.form = row;
					delete this.form.createTime;
					delete this.form.updateTime;
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

						httpUtils.paramPost(apiConfig.loginUser.success, {
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

						httpUtils.paramPost(apiConfig.loginUser.block, {
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

						httpUtils.paramPost(apiConfig.loginUser.deleteInfo, {
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
			formSubmit(formName) {

				this.$refs[formName].validate((valid) => {
					if (valid) {
						if (this.editModal.loading)
							return;

						this.editModal.loading = true;

						const loading = componentUtils.loading('操作中,请稍后');

						var self = this;
						httpUtils.paramPost(apiConfig.loginUser.update, this.form, (data) => {
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
		}
	}
</script>

<style lang="scss" scoped>

</style>
