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
 * 系统登录用户登录日志
 * @author jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserLoginLogVo implements Serializable{

	private static final long serialVersionUID = 7794669285253606814L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**登录用户*/
	@QueryColumn("user_id")
	private Long userId;
	
	/**登录时间*/
	@QueryColumn("login_time")
	private Date loginTime;
	
	/**登录Ip*/
	@QueryColumn("login_ip")
	private String loginIp;

}
