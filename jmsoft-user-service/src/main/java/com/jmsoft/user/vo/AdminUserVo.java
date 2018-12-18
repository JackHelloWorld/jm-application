package com.jmsoft.user.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmsoft.common.annotation.QueryConfig;
import com.jmsoft.common.annotation.ValidateEdit;
import com.jmsoft.common.annotation.QueryConfig.QueryType;
import com.jmsoft.common.annotation.ValidateEdit.ValidateType;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserVo implements Serializable{

	private static final long serialVersionUID = -7101017553115688539L;
	
	private String id;
	
	/**昵称*/
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(message="请输入昵称")
	@ApiParam("用户昵称")
	private String nickName;
	
	/**登录名称*/
	@ValidateEdit(message="请输入登录名")
	@QueryConfig(QueryType.LIKEBOTH)
	@ApiParam("登录名")
	private String loginName;
	
	/**角色名称*/
	@QueryConfig(QueryType.LIKEBOTH)
	@ApiParam("角色名称")
	private String roleName;
	
	/**登录密码*/
	@JsonIgnore
	@ApiParam(hidden=true)
	private String loginPwd;
	
	/**登录密码--加密基数*/
	@JsonIgnore
	@ApiParam(hidden=true)
	private String loginEncry;
	
	/**用户创建人*/
	@ValidateEdit(message="操作人有误",type=ValidateType.LONG)
	private String createUserId;
	
	/**描述信息*/
	@QueryConfig(QueryType.LIKEBOTH)
	@ApiParam("描述信息")
	private String adminDesc;
	
	/**用户创建时间*/
	private String createtime;
	
	/**最后一次登录时间*/
	@ApiParam(hidden=true)
	private String lastLoginTime;
	
	/**最后一次绑定时间*/
	@ApiParam(hidden=true)
	private String lastBindTime;
	
	@ApiParam(hidden=true)
	private String openId;
	
	@ApiParam(hidden=true)
	private String lastWeChatToken;
	
	/**头像地址*/
	private String headPortrait;
	
	/**是否为管理员*/
	@ApiParam(hidden=true)
	private String isAdmin;
	
	/**账号状态,0:正常,1:停用,2:已经删除*/
	@ApiParam(hidden=true)
	private String status;
	
	/**角色id*/
	@ValidateEdit(message="请选择角色信息",type=ValidateType.LONG)
	private String roleId;
	
	/**电话*/
	@QueryConfig(QueryType.LIKEBOTH)
	private String phone;
	
	/**最后一次修改时间*/
	@ApiParam(hidden=true)
	private String lastUpdateTime;
	
	/**最后一次修改操作人*/
	@ApiParam(hidden=true)
	private String lastUpdateUserId;
	
	/**删除操作员*/
	@ApiParam(hidden=true)
	private String deleteUserId;
	
	/**删除时间*/
	@ApiParam(hidden=true)
	private String deleteTime;
	

}
