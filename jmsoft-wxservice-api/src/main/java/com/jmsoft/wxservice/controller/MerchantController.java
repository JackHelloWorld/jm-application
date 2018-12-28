package com.jmsoft.wxservice.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.equipment.service.EquipmentService;
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
	public ResponseResult scanBind(@ApiParam("设备二维码信息") @RequestParam(defaultValue="",value="equipment_no") String equipmentNo) throws Exception {
		return equipmentService.scanBind(equipmentNo,getUser().getId());
	}
	
}
