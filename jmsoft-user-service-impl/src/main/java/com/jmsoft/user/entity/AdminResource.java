package com.jmsoft.user.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单信息
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admin_resource")
public class AdminResource implements Serializable{

	private static final long serialVersionUID = 7794669285253606814L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**资源名称*/
	@Column(name="r_text")
	private String text;
	
	/**资源简介*/
	@Column(name="r_intro")
	private String intro;
	
	/**资源样式*/
	@Column(name="r_style")
	private String style;
	
	/**事件函数*/
	@Column(name="r_click")
	private String clickAction;
	
	/**图标*/
	@Column(name="r_icon")
	private String icon;
	
	/**排序*/
	@Column(name="r_sort")
	private Integer sort;
	
	/**资源类型{0:菜单,1:按钮}*/
	@Column(name="r_type")
	private Integer type;
	
	/**url*/
	@Column(name="r_url")
	private String url;
	
	/**是否为管理员操作,{0:否,1:是}*/
	@Column(name="r_is_admin",columnDefinition="INT default 0")
	private Integer isAdmin;
	
	@Transient
	private boolean own;
	
	/**父级id,0为顶级*/
	@Column(name="parent_id")
	private Long parentId;
	
	/**子节点信息*/
	@Transient
	private List<AdminResource> nodes;

}
