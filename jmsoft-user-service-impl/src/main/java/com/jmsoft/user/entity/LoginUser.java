package com.jmsoft.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端登录用户
 * @author jack
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="login_user")
public class LoginUser implements Serializable{
	
	private static final long serialVersionUID = -7101017553115688539L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**头像地址*/
	@Column(name="profile_img")
	private String profileImg;
	
	/**昵称*/
	@Column(name="nick_name")
	private String nickName;
	
	/**地址*/
	@Column(name="address")
	private String address;
	
	/**更新时间*/
	@Column(name="update_time")
	private Date updateTime;
	
	/**姓名*/
	@Column(name="l_name")
	private String name;
	
	/**性别,字典*/
	@Column(name="l_sex")
	private String sex;
	
	/**手机号*/
	@Column(name="l_phone")
	private String phone;
	
	/**证件类型,字典*/
	@Column(name="certificate_type")
	private String certificateType;
	
	/**证件号*/
	@Column(name="certificate_no")
	private String certificateNo;
	
	/**创建时间*/
	@Column(name="create_time")
	private Date createTime;
	
	/**wx_open_id*/
	@Column(name="wx_open_id")
	private String wxOpenId;
	
	/**alipay_open_id*/
	@Column(name="alipay_open_id")
	private String alipayOpenId;
	
	/**状态,查询时有效:{0:正常,1:禁用,2:已删除}*/
	@Column(name="l_status")
	private Integer status;
	
	/**删除时间*/
	@Column(name="delete_time")
	private Date deleteTime;

	/**类型{0:普通用户,1:商家,2:市级代理,3:县级代理}*/
	@Column(name="user_type")
	private Integer userType;

	/**开户行名称*/
	@Column(name="bank_name")
	private String bankName;
	
	/**卡号*/
	@Column(name="bank_no")
	private String bankNo;
}
