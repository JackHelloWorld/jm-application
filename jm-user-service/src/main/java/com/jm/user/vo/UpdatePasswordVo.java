package com.jm.user.vo;

import java.io.Serializable;

import com.jm.sys.annotation.ValidateEdit;

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
	private Long id;
	
	/**原密码*/
	@ValidateEdit(message="请输入原密码")
	private String old_password;
	
	/**新密码*/
	@ValidateEdit(message="请输入新密码")
	private String new_password;
	
}
