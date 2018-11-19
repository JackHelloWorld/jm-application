package com.jm.sys.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigData {

	/*-----------dubbo config---------------*/
	@Value("${user.zookeeper.address}")
	public String zookeeperAddress;
	
	@Value("${user.dubbo.name}")
	public String dubboName;
	
	@Value("${user.dubbo.qos.port}")
	public Integer dubboQosPort;
	
	@Value("${user.dubbo.protocol.name}")
	public String dubboProtocolName;
	
	@Value("${user.dubbo.protocol.port}")
	public Integer dubboProtocolPort;
	
	
	
	//ftp 信息-----------------
	/**ftp登录ip*/
	@Value("${user.ftp.ip}")
	public String FTP_IP;
	
	/**ftp登录端口*/
	@Value("${user.ftp.prot}")
	public Integer FTP_PROT;
	
	/**ftp登录名*/
	@Value("${user.ftp.login.name}")
	public String FTP_LOGIN_NAME;
	
	/**ftp登录密码*/
	@Value("${user.ftp.login.password}")
	public String FTP_PASSWORD;
	
	/**ftp图片上传,读取地址*/
	@Value("${user.ftp.img.path}")
	public String ftpImgPath;
	
	/**ftp文件上传,读取地址*/
	@Value("${user.ftp.file.path}")
	public String ftpFilePath;

}
