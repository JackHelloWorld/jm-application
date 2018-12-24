package com.jmsoft.equipment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备信息
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="biz_equipment")
public class Equipment implements Serializable{

	private static final long serialVersionUID = 3921533798715797517L;

	/**设备id*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**创建时间*/
	@Column(name="e_create_time")
	private Date createTime;
	
	/**创建人*/
	@Column(name="e_create_user_id")
	private Long createUserId;
	
	/**删除操作人*/
	@Column(name="e_delete_user_id")
	private Long deleteUserId;
	
	/**删除时间*/
	@Column(name="e_delete_time")
	private Date deleteTime;
	
	/**状态,{0:初始状态,1:共享中,2:使用中,3:取消共享,4:已停用,9:已删除}*/
	@Column(name="e_status")
	private Integer status;
	
	/**经度*/
	@Column(name="e_longitude")
	private BigDecimal longitude;
	
	/**纬度*/
	@Column(name="e_latitude")
	private BigDecimal latitude;

	/**设备位置详情,中文描述*/
	@Column(name="e_address")
	private String address;
	
	/**最低启动金额*/
	@Column(name="e_least_money")
	private BigDecimal leastMoney;
	
	/**低于余额拉闸*/
	@Column(name="e_under_money")
	private BigDecimal underMoney;
	
	/**单次最大电量*/
	@Column(name="e_max_electric")
	private BigDecimal maxElectric;
	
	/**单价(元)*/
	@Column(name="e_price")
	private BigDecimal price;
	
	/**所属人*/
	@Column(name="e_login_user_id")
	private Long loginUserId;
	
	/**平台金额*/
	@Column(name="e_terrace_money")
	private BigDecimal terraceMoney;
	
	/**市级代理*/
	@Column(name="e_city_money")
	private BigDecimal cityMoney;
	
	/**市级代理人*/
	@Column(name="e_city_user_id")
	private Long cityUserId;
	
	/**县级代理*/
	@Column(name="e_county_money")
	private BigDecimal countyMoney;
	
	/**县级代理人*/
	@Column(name="e_county_user_id")
	private Long countyUserId;
	
	/**设备名称*/
	@Column(name="e_name")
	private String name;
	
	/**设备备注*/
	@Column(name="e_remark")
	private String remark;
	
	/**设备编号*/
	@Column(name="e_no")
	private String no;
	
	/**扫码二维码*/
	@Column(name="e_scan_qr_code")
	private String scanQrCode;
	
}