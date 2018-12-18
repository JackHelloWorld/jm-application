package com.jmsoft.user.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.jmsoft.common.annotation.QueryColumn;

import io.swagger.annotations.ApiParam;
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
	private Long id;

	/**资源名称*/
	@QueryColumn("r_text")
	@ApiParam("资源名称")
	private String text;
	
	/**资源简介*/
	@QueryColumn("r_intro")
	@ApiParam("资源简介")
	private String intro;
	
	/**资源样式*/
	@QueryColumn("r_style")
	@ApiParam("资源样式")
	private String style;
	
	/**事件函数*/
	@QueryColumn("r_click")
	@ApiParam("事件函数")
	private String clickAction;
	
	/**图标*/
	@ApiParam("图标")
	@QueryColumn("r_icon")
	private String icon;
	
	/**排序*/
	@ApiParam("排序")
	@QueryColumn("r_sort")
	private Integer sort;
	
	/**资源类型{0:菜单,1:按钮}*/
	@ApiParam("资源类型{0:菜单,1:按钮}")
	@QueryColumn("r_type")
	private Integer type;
	
	/**url*/
	@QueryColumn("r_url")
	@ApiParam("资源路径")
	private String url;
	
	/**是否为管理员操作,{0:否,1:是}*/
	@QueryColumn("r_is_admin")
	@ApiParam(hidden=true)
	private Integer isAdmin;
	
	@Transient
	@ApiParam(hidden=true)
	private boolean own;
	
	/**父级id,0为顶级*/
	@QueryColumn("parent_id")
	@ApiParam(value="父级id,0为顶级")
	private Long parentId;
	
	/**子节点信息*/
	@Transient
	@ApiParam(hidden=true)
	private List<AdminResourceVo> nodes;

}
