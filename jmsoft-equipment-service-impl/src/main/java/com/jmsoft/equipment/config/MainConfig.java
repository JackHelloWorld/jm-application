package com.jmsoft.equipment.config;


import javax.annotation.Resource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableConfigurationProperties(UserDbProperties.class)
public class MainConfig {

	@Resource
	UserDbProperties userDbProperties;
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(UserDbProperties userDbProperties) {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(userDbProperties.isShowSql());
		hibernateJpaVendorAdapter.setGenerateDdl(userDbProperties.isGenerateDdl());
		hibernateJpaVendorAdapter.setPrepareConnection(userDbProperties.isPrepareConnection());
		hibernateJpaVendorAdapter.setDatabase(userDbProperties.getDatabase());
		return hibernateJpaVendorAdapter;
	}

	@Bean(name = "userTransaction")
	public UserTransaction userTransaction(UserDbProperties userDbProperties) throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(userDbProperties.getTransactionTimeout());
		return userTransactionImp;
	}

	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
	public TransactionManager atomikosTransactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();

		AtomikosJtaPlatform.transactionManager = userTransactionManager;

		return userTransactionManager;
	}

	@Bean(name = "transactionManager")
	@DependsOn({ "userTransaction", "atomikosTransactionManager" })
	public PlatformTransactionManager transactionManager() throws Throwable {
		UserTransaction userTransaction = userTransaction(userDbProperties);

		AtomikosJtaPlatform.transaction = userTransaction;

		TransactionManager atomikosTransactionManager = atomikosTransactionManager();
		return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
	}

}