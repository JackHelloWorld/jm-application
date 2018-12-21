<template>
	<div id="dictionary" v-loading="loading">
		<el-row>
			<el-col :span="11">
				<el-card shadow="always">
					<div slot="header" class="clearfix">
						<span class="card-title">系统字典管理</span>
						<div style="float: right;">
							<el-button :key="action.text" v-for="action in actions" @click="actionGo(action.clickAction)" :type="action.style" :title="action.text" :icon="action.icon" circle></el-button>
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
								<el-form-item label="字典名称">
									<el-input v-model="form.text" :disabled="form.actionType == -1" placeholder="请输入字典名称" prop="text" :rules="[{ required: true, message: '字典名称不能为空'}]"></el-input>
								</el-form-item>
								<el-form-item label="英文名称">
									<el-input v-model="form.enText" :disabled="form.actionType == -1" placeholder="请输入英文名称" prop="enText" :rules="[{ required: true, message: '英文名称不能为空'}]"></el-input>
								</el-form-item>
								<el-form-item label="token">
									<el-input v-model="form.token" :disabled="form.actionType == -1" placeholder="请输入token校验码" prop="token" :rules="[{ required: true, message: 'token校验码不能为空'}]"></el-input>
								</el-form-item>
								<el-form-item label="值">
									<el-input v-model="form.value" :disabled="form.actionType == -1" placeholder="请输入值" prop="value" :rules="[{ required: true, message: '值不能为空'}]"></el-input>
								</el-form-item>
								<el-form-item label="排序">
									<el-input v-model="form.sort" :disabled="form.actionType == -1" type="number" placeholder="请输入排序字"></el-input>
								</el-form-item>
								<el-form-item>
									<el-button type="primary" :disabled="form.actionType == -1" @click="saveInfo('form')">保 存</el-button>
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
	import Actions from '../components/Actions.vue';
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
					enText: '',
					text: '',
					icon: '',
					token: '',
					value: '',
					sort: null,
				}
			}
		},
		components: {

		},
		mounted: function() {
			this.findDictionary();
			vueEvent.$on('actions', (data) => {
				this.actions = data;
			});
		},
		methods: {
			handleNodeClick(data) {
				this.form = {
					id : data.id,
					actionType: -1,
					enText: data.enText,
					text: data.text,
					icon: data.icon,
					token: data.token,
					value: data.value,
					sort: data.sort,
				};
				this.selectId = data.id;
				
			},
			findDictionary() {
				var self = this;
				httpUtils.paramPost(apiConfig.dictionary.findAllDictionary, {}, (data) => {
					this.data = data.data;
					this.form = {
						id : null,
						actionType: -1,
						enText: '',
						text: '',
						icon: '',
						token: '',
						value: '',
						sort: null,
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
			saveInfo(formName) {
				
				this.$refs[formName].validate((valid) => {
					if (valid) {
						var url = '';
						if(this.form.actionType == 1){
							url = apiConfig.dictionary.saveDictionary;
						}else if(this.form.actionType == 2){
							url = apiConfig.dictionary.updateDictionary;
						}
						
						const loading = componentUtils.loading('操作中,请稍后...');
						
						httpUtils.paramPost(url,this.form,(data)=>{
							loading.close();
							componentUtils.message.success('操作成功');
							this.findDictionary();
						},(err)=>{
							loading.close();
							componentUtils.message.error(err.msg);
						},function(){
							loading.close();
						});
					} else {
						return false;
					}
				});
			},
			addInfo(){
				
				var parentToken = this.form.token || '0';
				
				this.form = {
					id : null,
					actionType: 1,
					enText: '',
					text: '',
					icon: '',
					token: '',
					value: '',
					sort: null,
					parentToken:parentToken
				}
			},
			editInfo(){
				
				if(this.form.id == null){
					componentUtils.message.warning('未选择编辑的字典');
					return;
				}
				
				this.form.actionType=2;
			},
			deleteInfo(){
				if(this.selectId == null){
					componentUtils.message.warning('未选择需要删除的字典');
					return;
				}
				
				this.$confirm('将删除选中字典并且无法恢复, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					const loading = componentUtils.loading('删除中,请稍后...');
					httpUtils.paramPost(apiConfig.dictionary.deleteDictionary,{id:this.selectId},(data)=>{
						loading.close();
						componentUtils.message.success('删除成功');
						this.findDictionary();
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
