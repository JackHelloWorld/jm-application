package com.jmsoft.user.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jmsoft.common.annotation.QueryColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户资源权限
 * @author jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResourceUserVo implements Serializable{

	private static final long serialVersionUID = -4303765635628600857L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**角色id*/
	@QueryColumn("user_id")
	private Long userId;
	
	/**资源id*/
	@QueryColumn("resource_id")
	private Long resourceId;
	
	/**创建时间*/
	@QueryColumn("create_time")
	private Date createTime;
	
	/**授权操作人*/
	@QueryColumn("create_user")
	private Long createUser;

}
