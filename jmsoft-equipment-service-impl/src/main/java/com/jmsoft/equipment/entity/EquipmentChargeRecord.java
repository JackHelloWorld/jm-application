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
 * 设备充电记录
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="biz_equipment_charge_record")
public class EquipmentChargeRecord implements Serializable{

	private static final long serialVersionUID = 3921533798715797517L;

	/**设备id*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**充电起始时间*/
	@Column(name="e_start_time")
	private Date startTime;
	
	/**充电人*/
	@Column(name="e_user_id")
	private Long userId;

	/**截止时间*/
	@Column(name="e_end_time")
	private Date endTime;
	
	/**状态,{0:充电中,1:已归档,2:已删除}*/
	@Column(name="e_status")
	private Integer status;
	
	/**设备id*/
	@Column(name="e_equipment_id")
	private Long equipmentId;
	
	/**起始读数*/
	@Column(name="e_start_number")
	private BigDecimal startNumber;
	
	/**截止读数*/
	@Column(name="e_end_number")
	private BigDecimal endNumber;
	
	/**使用度数*/
	@Column(name="e_number")
	private BigDecimal number;

	/**总金额*/
	@Column(name="e_money")
	private String money;
	
	/**市级代理金额*/
	@Column(name="e_city_money")
	private BigDecimal cityMoney;
	
	/**低于余额拉闸*/
	@Column(name="e_under_money")
	private BigDecimal underMoney;
	
	/**单价(元)*/
	@Column(name="e_price")
	private BigDecimal price;
	
	/**平台金额*/
	@Column(name="e_terrace_money")
	private BigDecimal terraceMoney;
	
	/**市级代理人*/
	@Column(name="e_city_user_id")
	private Long cityUserId;
	
	/**县级代理金额*/
	@Column(name="e_county_money")
	private BigDecimal countyMoney;
	
	/**县级代理人*/
	@Column(name="e_county_user_id")
	private Long countyUserId;
	
}