package com.jm.user.vo;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.jm.common.annotation.QueryColumn;
import com.jm.common.annotation.QueryConfig;
import com.jm.common.annotation.QueryConfig.QueryType;
import com.jm.common.annotation.ValidateEdit;
import com.jm.common.annotation.ValidateEdit.ValidateType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色信息
 * @author liuji
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleVo implements java.io.Serializable{

	private static final long serialVersionUID = 8487218668365987190L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(type=ValidateType.NULL,message="请输入角色名称")
	@QueryColumn("r_name")
	private String name;
	
	@QueryConfig(QueryType.LIKEBOTH)
	@QueryColumn("r_remark")
	private String remark;
	
	@QueryColumn("create_time")
	private Date createTime;
	
	@QueryColumn("create_user_id")
	private Long createUserId;
	
	/**状态:{0:正常,1:停用,2:删除}*/
	@QueryColumn("r_status")
	private Integer status;
	
	@Transient
	private String createUserName;

}
