package com.jm.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jm.sys.annotation.QueryConfig;
import com.jm.sys.annotation.QueryConfig.QueryType;
import com.jm.sys.annotation.ValidateEdit;
import com.jm.sys.annotation.ValidateEdit.ValidateType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色信息
 * @author liuji
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admin_role")
public class AdminRole implements java.io.Serializable{

	private static final long serialVersionUID = 8487218668365987190L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(type=ValidateType.NULL,message="请输入角色名称")
	@Column(name="r_name")
	private String name;
	
	@QueryConfig(QueryType.LIKEBOTH)
	@Column(name="r_remark")
	private String remark;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="create_user_id")
	private Long createUserId;
	
	/**状态:{0:正常,1:禁用,2:删除}*/
	@Column(name="r_status")
	private Integer status;
	
	@Transient
	private String createUserName;

}
