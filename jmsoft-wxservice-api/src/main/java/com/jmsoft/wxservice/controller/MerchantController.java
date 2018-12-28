package com.jmsoft.wxservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.equipment.service.EquipmentService;
import com.jmsoft.equipment.vo.UpdateEquipmentInfoVo;
import com.jmsoft.wxservice.utils.BaseWeChatController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 商家服务
 * @author Jack
 *
 */
@RestController
@RequestMapping("merchant")
@Api(description="商家相关服务",tags="商家服务")
public class MerchantController extends BaseWeChatController{
	
	private static final long serialVersionUID = -2077518353093309227L;
	
	@Reference
	EquipmentService equipmentService;

	/**
	 * 商家扫码绑定设备
	 * @param equipmentNo 设备二维码信息
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("商家扫码绑定设备")
	@PostMapping("scan_bind")
	public ResponseResult scanBind(@ApiParam("设备二维码信息") @RequestParam(defaultValue="",value="equipment_no") String equipmentNo) throws Exception {
		return equipmentService.scanBind(equipmentNo,getUser().getId());
	}
	
	@ApiOperation("修改设备资料")
	@PostMapping("update/equipment/info")
	public ResponseResult updateEquipmentInfo(UpdateEquipmentInfoVo updateEquipmentInfoVo) throws Exception {
		return equipmentService.updateEquipmentInfo(updateEquipmentInfoVo,getUser().getId());
	}
	
	@ApiOperation("共享设备")
	@PostMapping("share")
	public ResponseResult share(@ApiParam(value="设备id",defaultValue="0",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception {
		return equipmentService.share(id,getUser().getId());
	}
	
	@ApiOperation("取消设备共享")
	@PostMapping("cancel/share")
	public ResponseResult cancelShare(@ApiParam(value="设备id",defaultValue="0",required=true) @RequestParam(value="id",defaultValue="0")Long id) throws Exception {
		return equipmentService.cancelShare(id,getUser().getId());
	}
	
}
