package com.jm.sys.vo;

import java.io.Serializable;

import javax.persistence.Id;

import com.jm.common.annotation.QueryColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 序列号记录
 * @author jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerialNumberVo implements Serializable{

	private static final long serialVersionUID = 2988989312275381148L;

	@Id
	private Long id;
	
	public SerialNumberVo(String columnName) {
		this.columnName = columnName;
	}

	/**列名*/
	@QueryColumn("f_column_name")
	private String columnName;

}
