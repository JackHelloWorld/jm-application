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
 * 设备操作信息
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="biz_equipment_operate_record")
public class EquipmentOperateRecord implements Serializable{

	private static final long serialVersionUID = 3921533798715797517L;

	/**设备id*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**操作时间*/
	@Column(name="e_create_time")
	private Date createTime;
	
	/**创建人*/
	@Column(name="e_create_user_id")
	private Long createUserId;
	
	/**创建人类型{0:后台用户,1:前端用户}*/
	@Column(name="user_type")
	private Integer userType;
	
	/**状态,{0:正常,9:已删除}*/
	@Column(name="e_status")
	private Integer status;
	
	/**关联设备id*/
	@Column(name="e_equipment_id")
	private Long equipmentId;
	
	/**操作前关联设备操作归档id*/
	@Column(name="e_equipment_record_info_old_id")
	private Long equipmentRecordInfoOldId;
	
	/**操作后关联设备操作归档id*/
	@Column(name="e_equipment_record_info_new_id")
	private Long equipmentRecordInfoNewId;
	
	
	
}