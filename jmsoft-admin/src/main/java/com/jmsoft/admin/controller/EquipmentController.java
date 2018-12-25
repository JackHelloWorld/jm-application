package com.jmsoft.admin.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.admin.utils.BaseAdminController;
import com.jmsoft.common.annotation.ValidateAuth;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.equipment.service.EquipmentService;
import com.jmsoft.equipment.vo.EquipmentVo;
import com.jmsoft.user.service.LoginUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("equipment")
@Api(description="设备相关服务",tags="设备控制器")
public class EquipmentController extends BaseAdminController{

	private static final long serialVersionUID = 304774933228615052L;
	
	@Reference
	EquipmentService equipmentService;
	
	@Reference
	LoginUserService loginUserService;
	
	@PostMapping("list")
	@ApiOperation("获取列表")
	public ResponseResult list(@ApiParam(value="页大小",defaultValue="10") @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
			@ApiParam(value="页码",defaultValue="1") @RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber,EquipmentVo equipmentVo) throws Exception{

		return equipmentService.list(equipmentVo,pageNumber,pageSize);
	}
		
	@PostMapping("update")
	@ApiOperation(value="修改信息",notes="需传入id")
	public ResponseResult update(EquipmentVo equipmentVo) throws Exception{
		return equipmentService.update(equipmentVo, getUser().getId());
	}
	
	@PostMapping("save")
	@ApiOperation(value="保存信息",notes="无需传入id")
	public ResponseResult save(EquipmentVo equipmentVo) throws Exception{
		return equipmentService.save(equipmentVo,getUser().getId());
	}
	
	@PostMapping("delete")
	@ApiOperation(value="删除信息")
	public ResponseResult delete(@ApiParam(value="设备id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws NumberFormatException, Exception{
		return equipmentService.delete(id,getUser().getId());
	}
	
	@PostMapping("success")
	@ApiOperation(value="启用设备")
	public ResponseResult success(@ApiParam(value="设备id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return equipmentService.success(id,getUser().getId());
	}
	
	@ApiOperation(value="停用设备")
	@PostMapping("block")
	public ResponseResult block(@ApiParam(value="设备id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return equipmentService.block(id,getUser().getId());
	}
	
	@ApiOperation(value="重置设备")
	@PostMapping("reset")
	public ResponseResult reset(@ApiParam(value="设备id",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception{
		return equipmentService.reset(id,getUser().getId());
	}
	
	@ApiOperation(value="获取代理用户用户,2:市集代理,2:县级代理")
	@PostMapping("get_proxy_user")
	@ValidateAuth({"/equipment/update","/equipment/save"})
	public ResponseResult findUserType() throws Exception{
		return loginUserService.findListByType(new Integer[] {2,3});
	}
	
}
