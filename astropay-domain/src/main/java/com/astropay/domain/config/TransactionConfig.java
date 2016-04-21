package com.astropay.domain.config;

import java.beans.PropertyVetoException;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.astropay.repository.config.DbConfig;

@Configuration
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
@ImportResource("classpath:tx-config.xml")
public class TransactionConfig implements TransactionManagementConfigurer {

	@Autowired
	private DbConfig database;

	@Bean(name = "transactionManager")
	public HibernateTransactionManager getHibernateTransactionManager()
			throws Exception {
		LocalSessionFactoryBean factory = this.database.getSessionFactory();
		SessionFactory sessionFactory = factory.getObject();
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);
		return transactionManager;
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		try {
			return this.getHibernateTransactionManager();
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Pointcut("execution(* com.astropay.domain.external..*.*(..))")
	public void external() {
	}

	@Pointcut("execution(* com.astropay.domain.service..*.*(..))")
	public void internal() {
	}

	@Pointcut("execution(* com.astropay.domain.integration..*.*(..))")
	public void integration() {
	}

	@Pointcut("execution(* com.astropay.domain.services.transactional.diagnostic..*.*(..))")
	public void support() {
	}

	@Pointcut("execution(* com.astropay.domain.mapper..*.*(..))")
	public void mapper() {
	}

	@Pointcut("execution(* com.astropay.repository.dao..*.*(..))")
	public void dao() {
	}
}
