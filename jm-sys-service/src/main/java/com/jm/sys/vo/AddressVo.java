package com.jm.sys.vo;

import java.io.Serializable;

import com.jm.common.annotation.QueryColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址信息
 * @author jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressVo implements Serializable{

	private static final long serialVersionUID = -3849369948083406757L;

	/**地址编码*/
	@QueryColumn("a_code")
	private String code;
	
	/**地址编码简写*/
	@QueryColumn("a_code_acronym")
	private String codeAcronym;
	
	/**父级code*/
	@QueryColumn("parent_code")
	private String parentCode;
	
	/**等级*/
	@QueryColumn("a_level")
	private Integer level;

	/**名称*/
	@QueryColumn("a_name")
	private String name;
	
	/**城乡分类代码*/
	@QueryColumn("a_countryside")
	private String countryside;
}
