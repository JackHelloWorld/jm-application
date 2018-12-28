package com.jmsoft.equipment.config;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

@Configuration
@DependsOn("transactionManager")
@EnableConfigurationProperties(UserDataSourceProperties.class)
@EnableJpaRepositories(basePackages= "com.jmsoft.user.repository",entityManagerFactoryRef = "userEntityManager", transactionManagerRef = "transactionManager")
public class UserDataSourceConfig {

	@Resource
	private JpaVendorAdapter jpaVendorAdapter;
	
	@Bean(name="userDataSource")
	public DataSource dataSource(UserDataSourceProperties userDataSourceProperties) throws SQLException {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(userDataSourceProperties.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(userDataSourceProperties.getPassword());
		mysqlXaDataSource.setUser(userDataSourceProperties.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setInitialTimeout(userDataSourceProperties.getInitialTimeout());
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setXaDataSource(mysqlXaDataSource);
		return atomikosDataSourceBean;
	}
	
	@Bean(name = "userEntityManager")
	public LocalContainerEntityManagerFactoryBean primaryEntityManager(UserDataSourceProperties userDataSourceProperties) throws Throwable {
 
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");
 
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(dataSource(userDataSourceProperties));
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan(userDataSourceProperties.getPackagesToScan());
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}
	
}
