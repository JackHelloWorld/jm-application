package com.jmsoft.equipment.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jmsoft.common.annotation.QueryColumn;
import com.jmsoft.common.annotation.QueryConfig;
import com.jmsoft.common.annotation.QueryConfig.QueryType;
import com.jmsoft.common.annotation.ValidateEdit;
import com.jmsoft.common.annotation.ValidateEdit.ValidateType;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备信息
 * @author jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentVo implements Serializable{

	private static final long serialVersionUID = 3921533798715797517L;

	@ApiParam(value="设备id,保存无需传入")
	private Long id;
	
	/**创建时间*/
	@QueryColumn("e_create_time")
	@ApiParam(hidden=true)
	private Date createTime;
	
	/**创建人*/
	@ApiParam(hidden=true)
	@QueryColumn("e_create_user_id")
	private Long createUserId;
	
	/**创建人名称*/
	@ApiParam("创建人名称,查询时有效")
	@QueryConfig(QueryType.LIKEBOTH)
	@QueryColumn("e_create_user_name")
	private String createUserName;
	
	/**删除操作人*/
	@ApiParam(hidden=true)
	@QueryColumn("e_delete_user_id")
	private Long deleteUserId;
	
	/**删除时间*/
	@ApiParam(hidden=true)
	@QueryColumn("e_delete_time")
	private Date deleteTime;
	
	/**状态,{0:初始状态,1:共享中,2:使用中,3:取消共享,4:已停用,9:已删除}*/
	@ApiParam("查询时有效")
	@QueryColumn("e_status")
	private Integer status;
	
	/**经度*/
	@ApiParam(hidden=true)
	@QueryColumn("e_longitude")
	private BigDecimal longitude;
	
	/**纬度*/
	@ApiParam(hidden=true)
	@QueryColumn("e_latitude")
	private BigDecimal latitude;

	/**设备位置详情,中文描述*/
	@ApiParam("设备位置详情")
	@QueryColumn("e_address")
	@QueryConfig(QueryType.LIKEBOTH)
	private String address;
	
	/**最低启动金额*/
	@ApiParam("最低启动金额")
	@QueryColumn("e_least_money")
	private BigDecimal leastMoney;
	
	/**低于余额拉闸*/
	@ApiParam("低于余额拉闸")
	@QueryColumn("e_under_money")
	private BigDecimal underMoney;
	
	/**单次最大电量*/
	@ApiParam("单次最大电量")
	@QueryColumn("e_max_electric")
	private BigDecimal maxElectric;
	
	/**单价(元)*/
	@ApiParam("单价(元)")
	@QueryColumn("e_price")
	private BigDecimal price;
	
	/**所属人*/
	@ApiParam(hidden=true)
	@QueryColumn("e_login_user_id")
	private Long loginUserId;
	
	/**平台金额*/
	@ApiParam("平台金额")
	@QueryColumn("e_terrace_money")
	private BigDecimal terraceMoney;
	
	/**市级代理*/
	@ApiParam("市级代理金额")
	@QueryColumn("e_city_money")
	private BigDecimal cityMoney;
	
	/**市级代理人*/
	@ApiParam("市级代理人")
	@QueryColumn("e_city_user_id")
	private Long cityUserId;
	
	/**市级代理人*/
	@ApiParam("市级代理人名称,查询时有效")
	@QueryConfig(QueryType.LIKEBOTH)
	@QueryColumn("e_city_user_name")
	private Long cityUserName;
	
	/**县级代理*/
	@ApiParam("县级代理金额")
	@QueryColumn("e_county_money")
	private BigDecimal countyMoney;
	
	/**县级代理人*/
	@ApiParam("县级代理金人")
	@QueryColumn("e_county_user_id")
	private Long countyUserId;
	
	/**县级代理人*/
	@ApiParam("县级代理金人名称,查询时有效")
	@QueryConfig(QueryType.LIKEBOTH)
	@QueryColumn("e_county_user_name")
	private Long countyUserName;
	
	/**设备名称*/
	@ApiParam("设备名称")
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(type=ValidateType.NULL,message="设备名称不能为空")
	@QueryColumn("e_name")
	private String name;
	
	/**设备备注*/
	@ApiParam("设备备注,说名")
	@QueryColumn("e_remark")
	@QueryConfig(QueryType.LIKEBOTH)
	private String remark;
	
	/**设备编号*/
	@ApiParam("设备编号")
	@QueryColumn("e_no")
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(type=ValidateType.NULL,message="设备编号不能为空")
	private String no;
	
	/**扫码二维码*/
	@ApiParam(hidden=true)
	@QueryColumn("e_scan_qr_code")
	private String scanQrCode;
	
}