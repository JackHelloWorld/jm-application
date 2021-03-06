package com.jmsoft.sys.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

import com.jmsoft.common.annotation.QueryColumn;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典信息
 * @author jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryVo implements Serializable{

	private static final long serialVersionUID = 7794669285253606814L;
	
	private Long id;

	/**显示名称*/
	@QueryColumn("d_text")
	@ApiParam(value="字典名称")
	private String text;
	
	/**英文名称*/
	@QueryColumn("d_en_text")
	@ApiParam(value="字典英文名称")
	private String enText;
	
	/**值*/
	@QueryColumn("d_value")
	@ApiParam(value="字典值")
	private String value;
	
	/**标识码*/
	@QueryColumn("d_token")
	@ApiParam(value="字典token")
	private String token;
	
	/**排序*/
	@QueryColumn("d_sort")
	@ApiParam(value="字典排序")
	private Integer sort;
	
	/**状态:{0:正常,1:已删除}*/
	@QueryColumn("d_status")
	@ApiParam(hidden=true)
	private Integer status;
	
	/**父级id,0为顶级*/
	@QueryColumn("d_parent_token")
	@ApiParam(value="父级token",defaultValue="0")
	private String parentToken;
	
	/**子节点信息*/
	@Transient
	private List<DictionaryVo> nodes;
}
