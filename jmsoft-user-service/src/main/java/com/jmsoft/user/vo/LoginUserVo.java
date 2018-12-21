package com.jmsoft.user.vo;

import java.io.Serializable;
import java.util.Date;

import com.jmsoft.common.annotation.QueryColumn;
import com.jmsoft.common.annotation.QueryConfig;
import com.jmsoft.common.annotation.QueryConfig.QueryType;
import com.jmsoft.common.annotation.ValidateEdit;
import com.jmsoft.common.annotation.ValidateEdit.ValidateType;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端登录用户
 * @author jack
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserVo implements Serializable{
	
	private static final long serialVersionUID = -7101017553115688539L;
	
	private Long id;
	
	/**头像地址*/
	@ApiParam("头像地址")
	@QueryColumn("profile_img")
	private String profileImg;
	
	@QueryColumn("nick_name")
	@QueryConfig(QueryType.LIKEBOTH)
	@ApiParam(required=true,value="昵称")
	@ValidateEdit(type=ValidateType.NULL,message="无昵称信息")
	private String nickName;
	
	/**地址*/
	@QueryColumn("address")
	@ValidateEdit(type=ValidateType.NULL,message="无地址信息")
	@ApiParam(required=true,value="地址")
	@QueryConfig(QueryType.LIKEBOTH)
	private String address;
	
	/**更新时间*/
	@ApiParam(hidden=true)
	@QueryColumn("update_time")
	private Date updateTime;
	
	/**姓名*/
	@QueryColumn("l_name")
	@QueryConfig(QueryType.LIKEBOTH)
	@ApiParam(required=true,value="真实名称")
	@ValidateEdit(type=ValidateType.NULL,message="请输入真实名称")
	private String name;
	
	/**性别,字典*/
	@QueryConfig
	@QueryColumn("l_sex")
	@ApiParam(required=true,value="性别")
	@ValidateEdit(type=ValidateType.NULL,message="请选择性别")
	private String sex;
	
	/**性别,字符串*/
	@QueryConfig
	@QueryColumn("l_sex_str")
	@ApiParam(hidden=true)
	private String sexStr;
	
	/**手机号*/
	@QueryColumn("l_phone")
	@QueryConfig(QueryType.LIKEBOTH)
	@ApiParam(required=true,value="电话")
	@ValidateEdit(type=ValidateType.PHONE,message="请输入手机号")
	private String phone;
	
	/**证件类型,字典*/
	@QueryConfig
	@QueryColumn("certificate_type")
	@ApiParam(required=true,value="证件类型")
	@ValidateEdit(type=ValidateType.NULL,message="请选择证件类型")
	private String certificateType;
	
	/**证件类型字符串*/
	@QueryConfig
	@QueryColumn("certificate_type_str")
	@ApiParam(hidden=true)
	private String certificateTypeStr;
	
	/**证件号*/
	@QueryColumn("certificate_no")
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(type=ValidateType.NULL,message="请输入证件号")
	private String certificateNo;
	
	/**创建时间*/
	@QueryColumn("create_time")
	@ApiParam(hidden=true)
	private Date createTime;
	
	/**状态*/
	@QueryColumn("l_status")
	@ApiParam("状态,查询时有效:{0:正常,1:禁用,2:已删除}")
	private Integer status;
	
	/**wx_open_id*/
	@ApiParam(hidden=true)
	@QueryColumn("wx_open_id")
	private String wxOpenId;
	
	/**alipay_open_id*/
	@ApiParam(hidden=true)
	@QueryColumn("alipay_open_id")
	private String alipayOpenId;
	
	
}
