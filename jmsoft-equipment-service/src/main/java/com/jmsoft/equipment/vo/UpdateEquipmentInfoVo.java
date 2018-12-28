package com.jmsoft.equipment.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.jmsoft.common.annotation.ValidateEdit;
import com.jmsoft.common.annotation.ValidateEdit.ValidateType;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改设备资料
 * @author Jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEquipmentInfoVo implements Serializable{
	
	private static final long serialVersionUID = 2230013948078885600L;

	@ApiParam("设备id")
	@ValidateEdit(type=ValidateType.NULL,message="无设备信息")
	private Long id;
	
	@ApiParam("经度")
	@ValidateEdit(type=ValidateType.NULL,message="无定位信息")
	private BigDecimal longitude;
	
	@ApiParam("纬度")
	@ValidateEdit(type=ValidateType.NULL,message="无定位信息")
	private BigDecimal latitude;

	@ApiParam("设备位置详情")
	@ValidateEdit(type=ValidateType.NULL,message="请输入位置详情")
	private String address;
	
	@ApiParam("单价(元)")
	@ValidateEdit(type=ValidateType.NULL,message="请输入设置单价")
	private BigDecimal price;	
	
	@ApiParam("设备备注,说明")
	private String remark;	
	
}
