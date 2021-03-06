package com.jmsoft.user.vo;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.jmsoft.common.annotation.QueryColumn;
import com.jmsoft.common.annotation.QueryConfig;
import com.jmsoft.common.annotation.ValidateEdit;
import com.jmsoft.common.annotation.QueryConfig.QueryType;
import com.jmsoft.common.annotation.ValidateEdit.ValidateType;

import io.swagger.annotations.ApiParam;
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
	@ApiParam(value="角色名称")
	private String name;
	
	@QueryConfig(QueryType.LIKEBOTH)
	@QueryColumn("r_remark")
	@ApiParam(value="角色描述")
	private String remark;
	
	@ApiParam(hidden=true)
	@QueryColumn("create_time")
	private Date createTime;
	
	@QueryColumn("create_user_id")
	@ApiParam(required=false,value="创建人id")
	private Long createUserId;
	
	/**状态:{0:正常,1:停用,2:删除}*/
	@ApiParam(hidden=false,value="状态 {0:正常,1:停用}")
	@QueryColumn("r_status")
	private Integer status;
	
	@Transient
	@ApiParam("创建人名称")
	private String createUserName;

}
