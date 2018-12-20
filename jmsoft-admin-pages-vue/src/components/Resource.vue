<template>
	<div id="resource" v-loading="loading">
		<el-row>
			<el-col :span="11">
				<el-card shadow="always">
					<div slot="header" class="clearfix">
						<span class="card-title">系统资源管理</span>
						<div style="float: right;">
							<el-button v-for="action in actions" @click="actionGo(action.clickAction)" :type="action.style" :title="action.text" :icon="action.icon" circle></el-button>
						</div>
					</div>
					<el-row style="max-height: 550px;min-height: 200px;overflow-y: scroll;">
						<el-col :span="24">
							<el-tree :data="data" :highlight-current="true" :props="{'label':'text','children':'nodes'}" @node-click="handleNodeClick"></el-tree>
						</el-col>
					</el-row>
				</el-card>
			</el-col>
			<el-col :span="12" style="float: right;">
				<el-card shadow="always">
					<div slot="header" class="clearfix">
						<span class="card-title" v-if="form.actionType == -1">详情</span>
						<span class="card-title" v-if="form.actionType == 1">新增</span>
						<span class="card-title" v-if="form.actionType == 2">修改</span>
					</div>
					<el-row>
						<el-col :span="24">
							<el-form ref="form" :model="form" label-width="80px">
								<el-form-item label="资源名称">
									<el-input v-model="form.text" :disabled="form.actionType == -1" placeholder="请输入资源名称"></el-input>
								</el-form-item>
								<el-form-item label="资源图标">
									<el-input v-model="form.icon" :disabled="form.actionType == -1" placeholder="请输入资源图标"></el-input>
								</el-form-item>
								<el-form-item label="资源描述">
									<el-input v-model="form.intro" :disabled="form.actionType == -1" placeholder="请输入描述信息"></el-input>
								</el-form-item>
								<el-form-item label="资源路径">
									<el-input v-model="form.url" :disabled="form.actionType == -1" placeholder="请输入资源路径"></el-input>
								</el-form-item>
								<el-form-item label="排序">
									<el-input v-model="form.sort" :disabled="form.actionType == -1" type="number" placeholder="请输入排序字"></el-input>
								</el-form-item>
								<el-form-item label="资源类型">
									<el-select v-model="form.type" :disabled="form.actionType == -1" style="width: 100%;" placeholder="请选择资源类型">
										<el-option label="菜单" :value="0"></el-option>
										<el-option label="按钮" :value="1"></el-option>
									</el-select>
								</el-form-item>
								<template v-if="form.type == 1">
									<el-form-item label="点击事件">
										<el-input v-model="form.clickAction" :disabled="form.actionType == -1" placeholder="请输入点击事件"></el-input>
									</el-form-item>
									<el-form-item label="资源样式">
										<el-input v-model="form.style" :disabled="form.actionType == -1" placeholder="请输入资源样式"></el-input>
									</el-form-item>
								</template>

								<el-form-item>
									<el-button type="primary" :disabled="form.actionType == -1" @click="saveInfo()">保 存</el-button>
								</el-form-item>
							</el-form>
						</el-col>
					</el-row>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import Actions from './Actions.vue';
	import httpUtils from '../utils/httpUtils.js';
	import apiConfig from '../utils/ApiConfig.js';
	import tools from '../utils/Tools.js';
	import componentUtils from '../utils/ComponentUtils.js';
	import vueEvent from '../utils/VueEvent.js';

	export default {
		data() {
			return {
				loading: true,
				data: [],
				actions: [],
				selectId : null,
				form: {
					id : null,
					actionType: -1,
					text: '',
					icon: '',
					intro: '',
					url: '',
					sort: '',
					type: null,
					clickAction: '',
					style: '',
				}
			}
		},
		components: {

		},
		mounted: function() {
			this.findResource();
			vueEvent.$on('actions', (data) => {
				this.actions = data;
			});
		},
		methods: {
			handleNodeClick(data) {
				this.form = {
					id : data.id,
					actionType: -1,
					text: data.text,
					icon: data.icon,
					intro: data.intro,
					url: data.url,
					sort: data.sort,
					type: data.type,
					clickAction: data.clickAction,
					style: data.style,
				};
				this.selectId = data.id;
				
			},
			findResource() {
				var self = this;
				httpUtils.paramPost(apiConfig.resource.findAllresources, {}, (data) => {
					this.data = data.data;
					this.form = {
						id : null,
						actionType: -1,
						text: '',
						icon: '',
						intro: '',
						url: '',
						sort: '',
						type: null,
						clickAction: '',
						style: '',
					};
					this.selectId=null;
					this.loading = false;
				}, (err) => {
					componentUtils.message.error(err.msg);
					this.loading = false;
				}, function() {
					self.loading = false;
				});
			},
			actionGo(cli){
				this[cli]();
			},
			saveInfo() {
				var url = '';
				if(this.form.actionType == 1){
					url = apiConfig.resource.saveResource;
				}else if(this.form.actionType == 2){
					url = apiConfig.resource.updateResource;
				}
				
				const loading = componentUtils.loading('操作中,请稍后...');
				
				httpUtils.paramPost(url,this.form,(data)=>{
					loading.close();
					componentUtils.message.success('操作成功');
					this.findResource();
				},(err)=>{
					loading.close();
					componentUtils.message.error(err.msg);
				},function(){
					loading.close();
				});
				
			},
			addInfo(){
				
				var parentId = this.form.id || 0;
				
				this.form = {
					id : null,
					actionType: 1,
					text: '',
					icon: '',
					intro: '',
					url: '',
					sort: '',
					type: null,
					clickAction: '',
					style: '',
					parentId:parentId
				}
			},
			editInfo(){
				
				if(this.form.id == null){
					componentUtils.message.warning('未选择编辑的资源');
					return;
				}
				
				this.form.actionType=2;
			},
			deleteInfo(){
				if(this.selectId == null){
					componentUtils.message.warning('未选择需要删除的资源');
					return;
				}
				
				this.$confirm('将删除选中资源并且无法恢复, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					const loading = componentUtils.loading('删除中,请稍后...');
					httpUtils.paramPost(apiConfig.resource.deleteResource,{id:this.selectId},(data)=>{
						loading.close();
						componentUtils.message.success('删除成功');
						this.findResource();
					},(err)=>{
						loading.close();
						componentUtils.message.error(err.msg);
					},function(){
						loading.close();
					});
				}).catch(() => {

				});
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
