package com.jmsoft.equipment.entity;

import java.io.Serializable;
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
 * 设备绑定记录
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="biz_equipment_bind_record")
public class EquipmentBindRecord implements Serializable{

	private static final long serialVersionUID = 2697559485500447549L;

	/**记录id*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**绑定时间*/
	@Column(name="e_bind_time")
	private Date bindTime;
	
	/**绑定人*/
	@Column(name="e_bind_user_id")
	private Long bindUserId;
	
	/**状态,{0:正常,9:已删除}*/
	@Column(name="e_status")
	private Integer status;
	
	/**设备id*/
	@Column(name="e_equipment_id")
	private Long equipmentId;
	
	/**生成二维码*/
	@Column(name="e_scan_qr_code")
	private String scanQrCode;
	
}