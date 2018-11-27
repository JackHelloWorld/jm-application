package com.jm.sys.vo;

import java.io.Serializable;

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

	private static final long serialVersionUID = 3134144347009444235L;
	
	/**地址编码*/
	private String code;
	
	/**地址编码简写*/
	private String codeAcronym;
	
	/**父级code*/
	private String parentCode;
	
	/**等级*/
	private Integer level;

	/**名称*/
	private String name;
	
	/**城乡分类代码*/
	private String countryside;
}
