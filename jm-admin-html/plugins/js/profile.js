var contentVue;
$(function() {
	contentVue = new Vue({
		el: '#page-content',
		data: {
			showTag: 'loginrecord',
			userInfo: userInfo,
			loginData: {
				page: {
					list: [],
					hasNextPage: false,
				},
				loginCount: '加载中',
				param: {
					pageNumber: 1,
					pageSize: 10
				}
			},
			subParam: {
				update_password: {
					old_password: '',
					new_password: '',
					new_password1: '',
					err_msg: ''
				},
				profile: {
					nickName: userInfo.nickName,
					address: userInfo.address,
					phone: userInfo.phone,
					intro: userInfo.intro,
					err_msg: ''
				},
			}
		},
		methods: {
			showTagClick: function(tag) {
				this.showTag = tag;
			},
			nextLoginLogPage: function() {
				if(contentVue.loginData.page.hasNextPage) {
					this.loginData.param.pageNumber = this.loginData.param.pageNumber + 1;
					findLoginInfo();
				}
			},
			updatePwdAction: function() {
				if(this.subParam.update_password.old_password == null || $.trim(this.subParam.update_password.old_password).length == 0) {
					this.subParam.update_password.err_msg = '请输入原密码';
					return;
				}
				if(this.subParam.update_password.new_password == null || $.trim(this.subParam.update_password.new_password).length == 0) {
					this.subParam.update_password.err_msg = "请输入新密码";
					return;
				}
				if(this.subParam.update_password.new_password1 != this.subParam.update_password.new_password) {
					this.subParam.update_password.err_msg = "确认密码输入有误";
					return;
				}
				this.subParam.update_password.err_msg = "";
				post(urlConfig.updatePassword, contentVue.subParam.update_password, function(data) {
					layer.msg(data.msg, {
						icon: 1
					});
				}, function(data) {
					contentVue.subParam.update_password.err_msg = data.msg;
				});
			},
			profileAction: function() {
				if(this.subParam.profile.nickName == null || $.trim(this.subParam.profile.nickName).length == 0) {
					this.subParam.profile.err_msg = '请输入名称';
					return;
				}
				if(this.subParam.profile.address == null || $.trim(this.subParam.profile.address).length == 0) {
					this.subParam.profile.err_msg = '请输入联系地址';
					return;
				}
				if(this.subParam.profile.phone == null || $.trim(this.subParam.profile.phone).length == 0) {
					this.subParam.profile.err_msg = '请输入联系电话';
					return;
				}
				this.subParam.profile.err_msg = '';
				post(urlConfig.updateProfile, contentVue.subParam.profile, function(data) {
					layer.msg(data.msg, {
						icon: 1
					});

					//加载修改信息
					userInfo.nickName = contentVue.subParam.profile.nickName;
					userInfo.address = contentVue.subParam.profile.address;
					userInfo.phone = contentVue.subParam.profile.phone;
					userInfo.intro = contentVue.subParam.profile.intro;
					navContentVue.userInfo = userInfo;
					navContent2.userInfo = userInfo;

					setData("login_user", JSON.stringify(userInfo));

				}, function(data) {
					contentVue.subParam.profile.err_msg = data.msg;
				});
			}
		},
		mounted: function() {
			countLoginInfo();
		}
	});
	findLoginInfo();
});

//统计登录信息
var countLoginInfo = function() {
	post(urlConfig.countLoginInfo, {}, function(data) {
		contentVue.loginData.loginCount = data.data;
	}, function(data) {

	});
};

var findLoginInfo = function() {
	post(urlConfig.findLoginInfo, contentVue.loginData.param, function(data) {
		contentVue.loginData.page.hasNextPage = data.data.hasNextPage;
		if(data.data.list.length > 0) {
			for(var i = 0; i < data.data.list.length; i++) {
				contentVue.loginData.page.list.push(data.data.list[i]);
			}
		}
	}, function(data) {

	});
}

var selectProfile = function() {
	layer.open({
		type: 1,
		title: '修改头像',
		shadeClose: false,
		area: ['70%', '90%'],
		content: $("#selectProfileModel"),
		btn: ['确定', '取消'],
		yes: function(index, layero) {
			if(selectAction.isSubmit) {
				selectAction.select(function(bloBdata) {
					//上传
					//创建formData对象
					var formData = new FormData();
					formData.append("file", bloBdata, "image.png");
					postForm(urlConfig.common.imgUpload, formData, function(data) {
						post(urlConfig.common.updateImgProfile, {
							profile: data.data.full_path
						}, function(result) {
							layer.msg(result.msg, {
								icon: 1
							});

							//更新头像
							userInfo.headPortrait = data.data.full_path;
							navContentVue.userInfo = userInfo;
							navContent2.userInfo = userInfo;
							setData("login_user", JSON.stringify(userInfo));
							layer.close(index);
						}, function(result) {
							layer.msg(result.msg, {
								icon: 7
							});
						});
					}, function(data) {
						layer.msg(data.msg, {
							icon: 2
						});
					});

				});
			} else {
				layer.msg("未选择头像,无法上传", {
					icon: 7
				});
			}

		},
		no: function(index, layero) {

		},
		success: function() {

		}
	});
}

//下面用于图片上传预览功能
function setImagePreview(avalue) {
	var docObj = document.getElementById("selectImgFileInput");
	var imgObjPreview = document.getElementById("imageTemp");
	if(docObj.files && docObj.files[0]) {
		//火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.maxWidth = '100%';
		imgObjPreview.style.height = '400px';
		//imgObjPreview.src = docObj.files[0].getAsDataURL();
		// 取绝对路径的getAsDataURL

		//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要以下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	} else {
		//IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		var localImagId = document.getElementById("localImag");
		//必须设置初始大小
		localImagId.style.maxWidth = "100%";
		localImagId.style.height = 'auto';
		//图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		} catch(e) {
			alert("您上传的图片格式不正确，请重新选择!");
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}

	return true;
};

let blob = ''

function each(arr, callback) {
	var length = arr.length;
	var i;
	for(i = 0; i < length; i++) {
		callback.call(arr, arr[i], i, arr);
	}
	return arr;
}

var selectAction = new Object();
selectAction.isSubmit = false;

function getRoundedCanvas(sourceCanvas) {
	var canvas = document.createElement('canvas');
	var context = canvas.getContext('2d');
	var width = sourceCanvas.width;
	var height = sourceCanvas.height;
	canvas.width = width;
	canvas.height = height;
	context.imageSmoothingEnabled = true;
	context.drawImage(sourceCanvas, 0, 0, width, height);
	context.globalCompositeOperation = 'destination-in';
	context.beginPath();
	context.arc(width / 2, height / 2, Math.min(width, height) / 2, 0, 2 * Math.PI, true);
	context.fill();
	return canvas;
}
var cropper;
selectAction.isSubmit = true;
var initSelect = function() {
	setImagePreview();
	var file = document.getElementById('selectImgFileInput');

	var image = document.querySelector('#imageTemp');
	$("#imageTemp").attr("src", image.src);
	var previews = document.querySelectorAll('.preview');
	if(cropper) {
		cropper = cropper.destroy();
	}

	cropper = new Cropper(image, {
		aspectRatio: 1,
		toggleDragModeOnDblclick: false,
		cropBoxResizable: false,
		cropBoxMovable: true,
		highlight: false,
		center: false,
		guides: false,
		restore: false,
		autoCropArea: 0.85,
		dragMode: 'move',
		movable: true,
		ready: function() {
			var clone = this.cloneNode();
			croppable = true;
			clone.className = ''
			clone.style.cssText = (
				'display: block;' +
				'width: 100%;' +
				'min-width: 0;' +
				'min-height: 0;' +
				'max-width: none;' +
				'max-height: none;'
			);
			each(previews, function(elem) {
				elem.appendChild(clone.cloneNode());
			});
		},
		// 类型： Function
		// 默认： null“ cropper” 事件的捷径。
		crop: function(e) {

			var data = e.detail;
			var cropper = this.cropper;
			var imageData = cropper.getImageData(); //输出图像位置，大小等相关数据。
			var previewAspectRatio = data.width / data.height;

			each(previews, function(elem) {

				$(elem).find("img").attr("src", image.src);

				var previewImage = elem.getElementsByTagName('img').item(0);
				var previewWidth = elem.offsetWidth;
				var previewHeight = previewWidth / previewAspectRatio;
				var imageScaledRatio = data.width / previewWidth;
				elem.style.height = previewHeight + 'px';
				previewImage.style.width = imageData.naturalWidth / imageScaledRatio + 'px';
				previewImage.style.height = imageData.naturalHeight / imageScaledRatio + 'px';
				previewImage.style.marginLeft = -data.x / imageScaledRatio + 'px';
				previewImage.style.marginTop = -data.y / imageScaledRatio + 'px';
			});
		},

	});
	cropper.reset();
	selectAction.select = function(success) {
		var croppedCanvas;
		var roundedCanvas;
		var roundedImage;

		if(!croppable) {
			return;
		}
		// Crop
		croppedCanvas = cropper.getCroppedCanvas();
		// Round
		roundedCanvas = getRoundedCanvas(croppedCanvas);
		blob = dataURLtoBlob(roundedCanvas.toDataURL());
		success(blob);
	}

	function dataURLtoBlob(dataurl) {
		var arr = dataurl.split(','),
			mime = arr[0].match(/:(.*?);/)[1],
			bstr = atob(arr[1]),
			n = bstr.length,
			u8arr = new Uint8Array(n);
		while(n--) {
			u8arr[n] = bstr.charCodeAt(n);
		}
		return new Blob([u8arr], {
			type: mime
		});
	}

};