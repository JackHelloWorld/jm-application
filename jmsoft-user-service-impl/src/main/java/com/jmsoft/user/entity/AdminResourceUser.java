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
 * 用户资源权限
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admin_resource_user")
public class AdminResourceUser implements Serializable{

	private static final long serialVersionUID = -4303765635628600857L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**角色id*/
	@Column(name="user_id")
	private Long userId;
	
	/**资源id*/
	@Column(name="resource_id")
	private Long resourceId;
	
	/**创建时间*/
	@Column(name="create_time")
	private Date createTime;
	
	/**授权操作人*/
	@Column(name="create_user")
	private Long createUser;

}
