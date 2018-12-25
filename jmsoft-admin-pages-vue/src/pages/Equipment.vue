<template>
	<div id="loginUser" v-loading="loading">
		<el-card class="box-card">
			<div slot="header" class="clearfix">
				<span class="card-title">操作栏</span>
			</div>
			<el-row>
				<el-col :span="24">
					<el-form :inline="true" class="demo-form-inline">
						<el-form-item label="创建人">
							<el-input v-model="queryData.createUserName" placeholder="创建人名称"></el-input>
						</el-form-item>
						<el-form-item label="地址">
							<el-input v-model="queryData.address" placeholder="地址"></el-input>
						</el-form-item>
						<el-form-item label="名称">
							<el-input v-model="queryData.name" placeholder="设备名称"></el-input>
						</el-form-item>
						<el-form-item label="编号">
							<el-input v-model="queryData.no" placeholder="设备号"></el-input>
						</el-form-item>
						<el-form-item label="市级代理人">
							<el-input v-model="queryData.cityUserName" placeholder="市级代理人名称"></el-input>
						</el-form-item>
						<el-form-item label="县级代理人">
							<el-input v-model="queryData.countyUserName" placeholder="县级代理人名称"></el-input>
						</el-form-item>
						<el-form-item label="备注">
							<el-input v-model="queryData.remark" placeholder="备注"></el-input>
						</el-form-item>
						<el-form-item label="状态">
							<el-select v-model="queryData.status" placeholder="请选择状态">
								<el-option label="初始状态" :value="0"></el-option>
								<el-option label="共享中" :value="1"></el-option>
								<el-option label="使用中" :value="2"></el-option>
								<el-option label="取消共享" :value="3"></el-option>
								<el-option label="已停用" :value="4"></el-option>
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
				<span class="card-title">设备列表</span>
			</div>

			<div>

				<el-table :data="page.list" style="width: 100%" @current-change="handleCurrentChange" border>
					<el-table-column prop="id" label="设备编号" width="150px">
						<template slot-scope="scope">
							<el-radio v-model="selectRow.id" :label="scope.row.id">{{ scope.row.no }}</el-radio>
						</template>
					</el-table-column>
					<el-table-column prop="name" label="设备名称" width="150px">
					</el-table-column>
					<el-table-column prop="cityUserName" label="市级代理人" width="150px">
					</el-table-column>
					<el-table-column prop="cityMoney" label="市级代理金额" width="150px">
					</el-table-column>
					<el-table-column prop="countyUserName" label="县级代理人" width="150px">
					</el-table-column>
					<el-table-column prop="countyMoney" label="县级代理金额" width="150px">
					</el-table-column>
					<el-table-column prop="terraceMoney" label="平台金额" width="150px">
					</el-table-column>
					<el-table-column prop="price" label="单价(元)" width="150px">
					</el-table-column>
					<el-table-column prop="maxElectric" label="单次最大电量" width="150px">
					</el-table-column>
					<el-table-column prop="underMoney" label="低于余额拉闸" width="150px">
					</el-table-column>
					<el-table-column prop="leastMoney" label="最低启动金额" width="150px">
					</el-table-column>
					<el-table-column prop="statusStr" label="状态" width="150px">
					</el-table-column>
					<el-table-column prop="createUserName" label="创建人" width="150px">
					</el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="200px">
					</el-table-column>
					<el-table-column prop="address" label="设备位置详情" width="250px">
					</el-table-column>
					<el-table-column prop="remark" label="备注" width="300px">
						<template slot-scope="scope">
							<span v-if="scope.row.remark && scope.row.remark.length <= 10" v-html="scope.row.remark"></span>
							<a href="javascript:;" v-if="scope.row.remark && scope.row.remark.length > 10" title="点击查看详情" v-html="scope.row.remark.substring(0,10)+'...'"
							 @click="showRemark(scope.row.remark)"></a>
						</template>
					</el-table-column>
				</el-table>
				<Pagination :page="page" queryMethod="pageQuery" :This="this" :queryData="queryData" />
			</div>
		</el-card>


		<!-- 编辑窗口 -->
		<el-dialog :title="editModal.title" :close-on-click-modal="false" :visible.sync="editModal.show" left>
			<el-form ref="form" :model="form" label-width="110px">
				<el-form-item label="设备编号" prop="no" :rules="[{ required: true, message: '设备编号不能为空'}]">
					<el-input v-model="form.no" placeholder="请输入设备编号">
					</el-input>
				</el-form-item>
				<el-form-item label="设备名称" prop="name" :rules="[{ required: true, message: '设备名称不能为空'}]">
					<el-input v-model="form.name" placeholder="请输入设备名称"></el-input>
				</el-form-item>
				<el-form-item label="平台金额">
					<el-input v-model="form.terraceMoney" type="number" :min="0" :step="0.01" placeholder="请输入平台金额"></el-input>
				</el-form-item>
				<el-form-item label="市级代理人">
					<el-select v-model="form.cityUserId" style="width: 100%;" placeholder="请选择市级代理人">
						<el-option :label="item.name" v-for="item in editModal.userInfos" v-if="item.userType == 2" :key="item.id" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="市级代理金额">
					<el-input v-model="form.cityMoney" type="number" :min="0" :step="0.01" placeholder="请输入市级代理金额"></el-input>
				</el-form-item>
				<el-form-item label="县级代理人">
					<el-select v-model="form.countyUserId" style="width: 100%;" placeholder="请选择县级代理人">
						<el-option :label="item.name"  v-if="item.userType == 3" v-for="item in editModal.userInfos" :key="item.id" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="县级代理金额">
					<el-input v-model="form.countyMoney" type="number" :min="0" :step="0.01" placeholder="请输入县级代理金额"></el-input>
				</el-form-item>
				<el-form-item label="单价(元)">
					<el-input v-model="form.price" type="number" :min="0" :step="0.01" placeholder="请输入单价"></el-input>
				</el-form-item>
				<el-form-item label="单次最大电量">
					<el-input v-model="form.maxElectric" type="number" :min="0" :step="0.01" placeholder="请输入单次最大电量"></el-input>
				</el-form-item>
				<el-form-item label="低于余额拉闸">
					<el-input v-model="form.underMoney" type="number" :min="0" :step="0.01" placeholder="请输入低于余额拉闸"></el-input>
				</el-form-item>
				<el-form-item label="最低启动金额">
					<el-input v-model="form.leastMoney" type="number" :min="0" :step="0.01" placeholder="请输入最低启动金额"></el-input>
				</el-form-item>
				<el-form-item label="位置详情">
					<el-input v-model="form.address" placeholder="请输入位置详情"></el-input>
				</el-form-item>
				<el-form-item label="设备备注">
					<el-input v-model="form.remark" type="textarea" placeholder="请输入设备备注"></el-input>
				</el-form-item>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button @click="editModal.show = false">取 消</el-button>
				<el-button type="primary" @click="formSubmit('form')">确 定</el-button>
			</span>
		</el-dialog>


		<el-dialog title="备注详情" :visible.sync="showModal.show" width="350px" center>
			<div style="width: 100%;word-wrap: break-word;word-break: break-all;" v-html="showModal.html"></div>
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
					loading: false,
					userInfos : []
				},
				page: {
					list: [],
					navigatepageNums: []
				},
				form: {
					no : '',
					name : '',
					terraceMoney : 0.2,
					cityUserId : null,
					cityMoney : 0,
					countyUserId : null,
					countyMoney : 0,
					price : 1.5,
					maxElectric : 60,
					underMoney : 0,
					leastMoney : 0,
					address : '',
					remark : ''
				},
				selectRow: {
					id: null
				},
				queryData: {
					nickName: '',
					address: '',
					name: '',
					sex: '',
					userType: null,
					phone: '',
					certificateType: '',
					certificateNo: '',
					status: '',
					pageSize: 10,
					pageNumber: 1,
				},
				showModal: {
					show: false,
					html: '',
				}
			}
		},
		components: {
			'Actions': Actions,
			'Pagination': Pagination
		},
		mounted: function() {
			this.query();
			httpUtils.paramPost(apiConfig.equipment.getProxyUser, {}, (data) => {
				this.editModal.userInfos = data.data;
				this.loading = false;
			}, (err) => {
				this.loading = false;
			});
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
			showRemark(remark) {
				this.showModal.html = remark;
				this.showModal.show = true;
			},
			addInfo() {
				this.form = {
					no : '',
					name : '',
					terraceMoney : 0.2,
					cityUserId : null,
					cityMoney : 0,
					countyUserId : null,
					countyMoney : 0,
					price : 1.5,
					maxElectric : 60,
					underMoney : 0,
					leastMoney : 0,
					address : '',
					remark : ''
				}
				this.editModal.title = '新增设备';
				this.editModal.type = 1;
				this.editModal.show = true;
			},
			findList() {
				const self = this;
				httpUtils.paramPost(apiConfig.equipment.list, this.queryData, (data) => {
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
				this.selectRow = {};
				this.findList();
			},
			updateInfo() {
				this.isSelectItem((row) => {
					console.log(row.cityUserId);
					this.form = {
						no : row.no,
						id : row.id,
						name : row.name,
						terraceMoney : row.terraceMoney || 0.2,
						cityUserId : row.cityUserId,
						cityMoney : row.cityMoney || 0,
						countyUserId : row.countyUserId,
						countyMoney : row.countyMoney || 0,
						price : row.price || 1.5,
						maxElectric : row.maxElectric || 60,
						underMoney : row.underMoney || 0,
						leastMoney : row.leastMoney || 0,
						address : row.address,
						remark : row.remark
					}
				
					this.editModal.title = '修改设备';
					this.editModal.type = 2;
					this.editModal.show = true;
				}, '请选择需要修改的信息');
			},
			success() {
				this.isSelectItem((row) => {

					this.$confirm('将启用选中设备, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {

						httpUtils.paramPost(apiConfig.equipment.success, {
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

					this.$confirm('将停用选中设备, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {

						httpUtils.paramPost(apiConfig.equipment.block, {
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

					this.$confirm('将删除选中设备并且无法恢复, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {

						httpUtils.paramPost(apiConfig.equipment.deleteInfo, {
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
			resetInfo() {
				this.isSelectItem((row) => {

					this.$confirm('将重置选中设备并且无法恢复, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {

						httpUtils.paramPost(apiConfig.equipment.reset, {
							id: row.id
						}, (data) => {
							this.query();
							componentUtils.message.success(data.msg);
						}, (err) => {
							componentUtils.message.error(err.msg);
						});

					}).catch(() => {

					});

				}, '请选择需要重置的信息');
			},
			formSubmit(formName) {

				this.$refs[formName].validate((valid) => {
					if (valid) {
						if (this.editModal.loading)
							return;

						this.editModal.loading = true;

						const loading = componentUtils.loading('操作中,请稍后');

						var self = this;

						var url = apiConfig.equipment.update;
						if (this.editModal.type == 1) {
							url = apiConfig.equipment.save;
						}

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
		}
	}
</script>

<style lang="scss" scoped>

</style>
