<template>

	<div id="commonHeader">

		<el-row type="flex">
			<el-col :span="24" justify="end" style="text-align: right;color: #FFFFFF;">
				<span>欢迎您:<span v-html="userInfo.nickName"></span>!</span>&nbsp;&nbsp;
				<span v-html="userInfo.roleName"></span>
				<el-button type="primary" @click.native="upodatePass()">修改密码</el-button>
				<el-button type="primary" @click.native="logout()">退出</el-button>
			</el-col>
		</el-row>

		<el-dialog title="密码修改" :visible.sync="updatePassModal.dialogVisible" width="600px" :close-on-click-modal="false">
			<el-form ref="form" :model="updatePassModal.form" label-width="80px">
				<el-form-item label="原密码" prop="old_password" :rules="[{ required: true, message: '原密码不能为空'}]">
					<el-input v-model="updatePassModal.form.old_password" type="password" placeholder="请输入原密码">
					</el-input>
				</el-form-item>
				<el-form-item label="新密码" prop="new_password" :rules="[{ required: true, message: '新密码不能为空'}]">
					<el-input v-model="updatePassModal.form.new_password" type="password" placeholder="请输入新密码"></el-input>
				</el-form-item>
				<el-form-item label="确认" prop="new_password1" :rules="[{ required: true, message: '确认密码不能为空'}]">
					<el-input v-model="updatePassModal.form.new_password1" type="password" placeholder="请输入确认密码"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="updatePassModal.dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="saveUpdatePass('form')">确 定</el-button>
			</span>
		</el-dialog>


	</div>

</template>

<script>
	import cacheUtils from '../utils/CacheUtils.js';
	import httpUtils from '../utils/httpUtils.js';
	import apiConfig from '../utils/ApiConfig.js';
	import tools from '../utils/Tools.js';
	import componentUtils from '../utils/ComponentUtils.js';
	
	export default {
		data() {
			return {
				userInfo: {
					nickName: '',
				},
				updatePassModal: {
					dialogVisible: false,
					form: {
						old_password: '',
						new_password: '',
						new_password1: '',
					}
				}
			};
		},
		methods: {
			logout() {
				this.$router.push("/");
			},
			upodatePass() {
				this.updatePassModal.dialogVisible = true;
				this.updatePassModal.form = {
					old_password: '',
					new_password: '',
					new_password1: '',
				}
			},
			saveUpdatePass(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						
						if(this.updatePassModal.form.new_password != this.updatePassModal.form.new_password1){
							componentUtils.message.error('确认密码输入不一致');
							return;
						}
						
						const loading = componentUtils.loading('操作中,请稍后');
						var self = this;
						httpUtils.paramPost(apiConfig.updatePassword, this.updatePassModal.form, (data) => {
							loading.close();
							componentUtils.message.success('密码修改成功');
							this.updatePassModal.dialogVisible = false;
						}, (err) => {
							loading.close();
							componentUtils.message.error(err.msg);
						}, function() {
							loading.close();
						});
					} else {
						return false;
					}
				});
			}
		},
		mounted: function() {
			this.userInfo = cacheUtils.getUserInfo();
		}
	}
</script>
