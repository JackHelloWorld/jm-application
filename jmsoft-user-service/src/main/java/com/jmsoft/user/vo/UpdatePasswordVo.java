package com.jmsoft.user.vo;

import java.io.Serializable;

import com.jmsoft.common.annotation.ValidateEdit;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改密码封装对象
 * @author Jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordVo  implements Serializable{

	private static final long serialVersionUID = 4036086438059973095L;

	/**用户id,需调用者传入*/
	@ValidateEdit(message="无用户信息,无法修改")
	@ApiParam(hidden=true)
	private Long id;
	
	/**原密码*/
	@ValidateEdit(message="请输入原密码")
	@ApiParam(value="原密码",required=true)
	private String old_password;
	
	/**新密码*/
	@ValidateEdit(message="请输入新密码")
	@ApiParam(value="新密码",required=true)
	private String new_password;
	
}
