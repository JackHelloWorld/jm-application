package com.jm.user.vo;

import java.io.Serializable;

import com.jm.sys.annotation.ValidateEdit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录请求参数封装数据
 * @author Jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserLoginVo implements Serializable{
	
	private static final long serialVersionUID = 5225091516850464802L;
	
	/**登录名*/
	@ValidateEdit(message="请输入登录名")
	private String login_name;
	
	/**登录密码*/
	@ValidateEdit(message="请输入密码")
	private String password;
	
	/**登录凭证*/
	private String session_key;
	
	/**登录验证码*/
	private String code;
	
	/**客户端ip*/
	@ValidateEdit(message="客户端信息有误")
	private String ip;

}
