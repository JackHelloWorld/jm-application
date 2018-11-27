package com.jm.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jm.user.vo.AdminResourceVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作员
 * @author jack
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admin_user")
public class AdminUser implements Serializable{
	
	private static final long serialVersionUID = -7101017553115688539L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**昵称*/
	@Column(name="nick_name")
	private String nickName;
	
	/**登录名称*/
	@Column(name="login_name")
	private String loginName;
	
	/**登录密码*/
	@JsonIgnore
	@Column(name="login_pwd")
	private String loginPwd;
	
	/**登录密码--加密基数*/
	@JsonIgnore
	@Column(name="login_encry")
	private String loginEncry;
	
	/**描述信息*/
	@Column(name="admin_desc")
	private String adminDesc;
	
	/**用户创建时间*/
	@Column(name="createtime")
	private Date createtime;
	
	/**用户创建人*/
	@Column(name="create_user")
	private Long createUserId;
	
	/**最后一次登录时间*/
	@Column(name="last_login_time")
	private Date lastLoginTime;
	
	/**最后一次绑定时间*/
	@Column(name="last_bind_time")
	private Date lastBindTime;
	
	@Column(name="open_id")
	private String openId;
	
	@Column(name="last_update_time")
	private Date lastUpdateTime;
	
	@Column(name="last_update_user_id")
	private Long lastUpdateUserId;
	
	@Column(name="delete_user_id")
	private Long deleteUserId;
	
	@Column(name="delete_time")
	private Date deleteTime;
	
	@JsonIgnore
	@Column(name="last_wechat_token")
	private String lastWeChatToken;
	
	/**头像地址*/
	@Column(name="head_portrait")
	private String headPortrait;
	
	/**是否为老板*/
	@Column(name="is_admin")
	private Boolean isAdmin;
	
	/**账号状态,0:正常,1:停用,2:已经删除*/
	@Column(name="u_status",columnDefinition="INT default 0")
	private Integer status;
	
	/**角色id*/
	@Column(name="role_id")
	private Long roleId;
	
	/**角色名称*/
	@Transient
	private String roleName;
	
	/**电话*/
	@Column(name="phone")
	private String phone;
	
	/**邮箱*/
	@Column(name="u_email")
	private String email;
	
	/**我的简介*/
	@Column(name="u_intro")
	private String intro;
	
	/**地址信息*/
	@Column(name="u_address")
	private String address;
	
	@Transient
	private String token;
	
	@Transient
	private AdminRole adminRole;
	
	/**资源信息 */
	@Transient
	@JsonIgnore
	private List<AdminResourceVo> adminResources;
}
