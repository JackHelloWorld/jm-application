package com.jmsoft.generate.address;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 数据库配置
 * @author Jack
 *
 */
public class Config {
	
	public static String driverClassName;
	public static String db_url;
	public static String db_username;
	public static String db_password;
	public static String find_level;
	public static String find_url;

	static {
		Properties p=new Properties();  //创建Properties对象
		try {
			String path=Thread.currentThread().getContextClassLoader().getResource ("application.properties").getPath();
			System.out.println(path);
			p.load(new FileInputStream(path));//读取.preperties中的信息
			p.list(System.out);//System.out 是打印到控制台
			driverClassName = p.getProperty("spring.datasource.driver-class-name");
			db_url = p.getProperty("spring.datasource.url");
			db_username = p.getProperty("spring.datasource.username");
			db_password = p.getProperty("spring.datasource.password");
			find_level = p.getProperty("spring.user.level");
			find_url = p.getProperty("spring.user.find.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
