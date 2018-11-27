package com.jm.user.vo;

import java.io.Serializable;

import com.jm.common.annotation.ValidateEdit;
import com.jm.common.annotation.ValidateEdit.ValidateType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户资料修改业务对象
 * @author Jack
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserProfileVo  implements Serializable{

	private static final long serialVersionUID = 4036086438059973095L;

	/**用户id,需调用者传入*/
	@ValidateEdit(message="无用户信息,无法修改")
	private Long id;
	
	/**用户名*/
	@ValidateEdit(message="请输入名称")
	private String nickName;
	
	/**联系地址*/
	@ValidateEdit(message="请输入联系地址")
	private String address;
	
	/**联系电话*/
	@ValidateEdit(message="联系电话有误",type=ValidateType.PHONE)
	private String phone;
	
	/**个人简介 */
	private String intro;
}
