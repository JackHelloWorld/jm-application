package com.jm.user.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jm.common.annotation.QueryColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作员
 * @author jack
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDbVo implements Serializable{
	
	private static final long serialVersionUID = -7101017553115688539L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**昵称*/
	@QueryColumn("nick_name")
	private String nickName;
	
	/**登录名称*/
	@QueryColumn("login_name")
	private String loginName;
	
	/**登录密码*/
	@JsonIgnore
	@QueryColumn("login_pwd")
	private String loginPwd;
	
	/**登录密码--加密基数*/
	@JsonIgnore
	@QueryColumn("login_encry")
	private String loginEncry;
	
	/**描述信息*/
	@QueryColumn("admin_desc")
	private String adminDesc;
	
	/**用户创建时间*/
	@QueryColumn("createtime")
	private Date createtime;
	
	/**用户创建人*/
	@QueryColumn("create_user")
	private Long createUserId;
	
	/**最后一次登录时间*/
	@QueryColumn("last_login_time")
	private Date lastLoginTime;
	
	/**最后一次绑定时间*/
	@QueryColumn("last_bind_time")
	private Date lastBindTime;
	
	@QueryColumn("open_id")
	private String openId;
	
	@QueryColumn("last_update_time")
	private Date lastUpdateTime;
	
	@QueryColumn("last_update_user_id")
	private Long lastUpdateUserId;
	
	@QueryColumn("delete_user_id")
	private Long deleteUserId;
	
	@QueryColumn("delete_time")
	private Date deleteTime;
	
	@JsonIgnore
	@QueryColumn("last_wechat_token")
	private String lastWeChatToken;
	
	/**头像地址*/
	@QueryColumn("head_portrait")
	private String headPortrait;
	
	/**是否为老板*/
	@QueryColumn("is_admin")
	private Boolean isAdmin;
	
	/**账号状态,0:正常,1:停用,2:已经删除*/
	@QueryColumn("u_status")
	private Integer status;
	
	/**角色id*/
	@QueryColumn("role_id")
	private Long roleId;
	
	/**角色名称*/
	@Transient
	private String roleName;
	
	/**电话*/
	@QueryColumn("phone")
	private String phone;
	
	/**邮箱*/
	@QueryColumn("u_email")
	private String email;
	
	/**我的简介*/
	@QueryColumn("u_intro")
	private String intro;
	
	/**地址信息*/
	@QueryColumn("u_address")
	private String address;
	
	@Transient
	private String token;
	
	@Transient
	private AdminRoleVo adminRole;
	
	/**资源信息 */
	@Transient
	@JsonIgnore
	private List<AdminResourceVo> adminResources;
}
