package com.jmsoft.user.entity;

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
 * 前端登录用户状态变动记录
 * @author jack
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="login_user_status_record")
public class LoginUserStatusRecord implements Serializable{
	
	private static final long serialVersionUID = -7101017553115688539L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**用户id*/
	@Column(name="login_user_id")
	private Long loginUserId;
	
	/**操作员id*/
	@Column(name="admin_user_id")
	private Long adminUserId;
	
	/**状态,查询时有效:{0:正常,1:禁用,2:已删除}*/
	@Column(name="l_status")
	private Integer status;
	
	/**原状态,查询时有效:{0:正常,1:禁用,2:已删除}*/
	@Column(name="l_old_status")
	private Integer oldStatus;
	
	/**操作时间*/
	@Column(name="create_time")
	private Date createTime;
	
}
