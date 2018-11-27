package com.jm.user.entity;

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
 * 操作员状态变动记录
 * @author jack
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admin_user_status_record")
public class AdminUserStatusRecord implements Serializable{
	
	private static final long serialVersionUID = -7101017553115688539L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**用户id*/
	@Column(name="admin_user_id")
	private Long adminUserId;
	
	/**操作人*/
	@Column(name="create_user_id")
	private Long createUserId;
	
	/**操作时间*/
	@Column(name="create_time")
	private Date createTime;
	
	/**原状态*/
	@Column(name="old_status")
	private Integer oldStatus;
	
	/**当前状态*/
	@Column(name="new_status")
	private Integer newStatus;
	
}
