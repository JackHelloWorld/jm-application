package com.jm.user.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jm.common.annotation.QueryColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作员状态变动记录
 * @author jack
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserStatusRecordVo implements Serializable{
	
	private static final long serialVersionUID = -7101017553115688539L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**用户id*/
	@QueryColumn("admin_user_id")
	private Long adminUserId;
	
	/**操作人*/
	@QueryColumn("create_user_id")
	private Long createUserId;
	
	/**操作时间*/
	@QueryColumn("create_time")
	private Date createTime;
	
	/**原状态*/
	@QueryColumn("old_status")
	private Integer oldStatus;
	
	/**当前状态*/
	@QueryColumn("new_status")
	private Integer newStatus;
	
}
