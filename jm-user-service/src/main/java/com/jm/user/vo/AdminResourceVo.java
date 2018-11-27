package com.jm.user.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.jm.common.annotation.QueryColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单信息
 * @author jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResourceVo implements Serializable{

	private static final long serialVersionUID = 7794669285253606814L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**资源名称*/
	@QueryColumn("r_text")
	private String text;
	
	/**资源简介*/
	@QueryColumn("r_intro")
	private String intro;
	
	/**资源样式*/
	@QueryColumn("r_style")
	private String style;
	
	/**事件函数*/
	@QueryColumn("r_click")
	private String clickAction;
	
	/**图标*/
	@QueryColumn("r_icon")
	private String icon;
	
	/**排序*/
	@QueryColumn("r_sort")
	private Integer sort;
	
	/**资源类型{0:菜单,1:按钮}*/
	@QueryColumn("r_type")
	private Integer type;
	
	/**url*/
	@QueryColumn("r_url")
	private String url;
	
	/**是否为管理员操作,{0:否,1:是}*/
	@QueryColumn("r_is_admin")
	private Integer isAdmin;
	
	@Transient
	private boolean own;
	
	/**父级id,0为顶级*/
	@QueryColumn("parent_id")
	private Long parentId;
	
	/**子节点信息*/
	@Transient
	private List<AdminResourceVo> nodes;

}
