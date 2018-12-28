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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

@Configuration
@DependsOn("transactionManager")
@EnableConfigurationProperties(PrimaryDataSourceProperties.class)
@EnableJpaRepositories(basePackages= "com.jmsoft.equipment.repository",entityManagerFactoryRef = "primaryEntityManager", transactionManagerRef = "transactionManager")
public class PrimaryDataSourceConfig {

	@Resource
	private JpaVendorAdapter jpaVendorAdapter;
	
	@Primary
	@Bean(name="primaryDataSource")
	public DataSource dataSource(PrimaryDataSourceProperties primaryDataSourceProperties) throws SQLException {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(primaryDataSourceProperties.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(primaryDataSourceProperties.getPassword());
		mysqlXaDataSource.setUser(primaryDataSourceProperties.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setInitialTimeout(primaryDataSourceProperties.getInitialTimeout());
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setXaDataSource(mysqlXaDataSource);
		return atomikosDataSourceBean;
	}
	
	@Primary
	@Bean(name = "primaryEntityManager")
	public LocalContainerEntityManagerFactoryBean primaryEntityManager(PrimaryDataSourceProperties primaryDataSourceProperties) throws Throwable {
 
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");
 
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(dataSource(primaryDataSourceProperties));
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan(primaryDataSourceProperties.getPackagesToScan());
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}
	
}
