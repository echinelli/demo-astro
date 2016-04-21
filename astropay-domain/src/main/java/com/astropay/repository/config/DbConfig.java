package com.astropay.repository.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DbConfig {

	private static final String ENTITIES_PACKAGE = "com.astropay.repository.entities";
	private static final String PREFERRED_TEST_QUERY = "/* ping */ SELECT 1";

	@Value("${jdbc.url}")
	private String url;

	@Value("${database.user}")
	private String username;

	@Value("${database.pass}")
	private String password;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Value("${hibernate.show_sql}")
	private Boolean showSql;

	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2ddlAuto;

	@Value("${hibernate.connection.autocommit}")
	private Boolean autocommit;

	@Value("${hibernate.format_sql}")
	private String formatSql;

	@Value("${hibernate.generate_statistics}")
	private String generateStatistics;

	@Value("${hibernate.query.factory_class}")
	private String queryFactoryClass;

	@Value("${hibernate.bytecode.use_reflection_optimizer}")
	private String bytecodeUseReflectionOptimizer;

	@Value("${hibernate.connection.isolation}")
	private String hibernateConnectionIsolation;

	// Hikari
	@Value("${hikari.connectionTimeout}")
	private Long connectionTimeout;

	@Value("${hikari.dataSourceClassName}")
	private String dataSourceClassName;

	@Value("${hikari.maximumPoolSize}")
	private int maximumPoolSize;

	@Value("${hikari.isolationLevel}")
	private String isolationLevel;

	@Value("${hikari.cachePrepStmts}")
	private String cachePrepStmts;

	@Value("${hikari.prepStmtCacheSize}")
	private String prepStmtCacheSize;

	@Value("${hikari.useServerPrepStmts}")
	private String useServerPrepStmts;

	@Value("${hikari.prepStmtCacheSqlLimit}")
	private String prepStmtCacheSqlLimit;

	@Bean(name = "dataSource")
	public LazyConnectionDataSourceProxy getDataSource()
			throws PropertyVetoException {
		DataSource targetDataSource = this.getMainDataSource();
		LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy(
				targetDataSource);
		return dataSource;
	}

	@Bean(name = "mainDataSource", destroyMethod = "close")
	public HikariDataSource getMainDataSource() throws PropertyVetoException {
		HikariConfig config = new HikariConfig();

		config.setConnectionTimeout(this.connectionTimeout);
		config.setDataSourceClassName(this.dataSourceClassName);
		config.setUsername(this.username);
		config.setPassword(this.password);
		config.setMaximumPoolSize(this.maximumPoolSize);
		config.setTransactionIsolation(this.isolationLevel);
		config.setConnectionTestQuery(PREFERRED_TEST_QUERY);

		// Properties
		config.addDataSourceProperty("url", this.url);
		config.addDataSourceProperty("cachePrepStmts", this.cachePrepStmts);
		config.addDataSourceProperty("prepStmtCacheSize",
				this.prepStmtCacheSize);
		config.addDataSourceProperty("prepStmtCacheSqlLimit",
				this.prepStmtCacheSqlLimit);
		config.addDataSourceProperty("useServerPrepStmts",
				this.useServerPrepStmts);

		return new HikariDataSource(config);
	}

	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean getSessionFactory()
			throws PropertyVetoException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setPackagesToScan(ENTITIES_PACKAGE);
		DataSource dataSource = this.getDataSource();
		sessionFactory.setDataSource(dataSource);

		Properties hibernateProperties = this.getHibernateProperties();
		sessionFactory.setHibernateProperties(hibernateProperties);

		return sessionFactory;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();

		properties.put("hibernate.dialect", this.hibernateDialect);
		properties.put("hibernate.query.factory_class", this.queryFactoryClass);
		properties.put("hibernate.bytecode.use_reflection_optimizer",
				this.bytecodeUseReflectionOptimizer);
		properties.put("hibernate.hbm2ddl.auto", this.hbm2ddlAuto);
		properties.put("hibernate.show_sql", this.showSql);
		properties.put("hibernate.format_sql", this.formatSql);
		properties
				.put("hibernate.generate_statistics", this.generateStatistics);
		properties.put("hibernate.connection.autocommit", this.autocommit);
		properties.put("hibernate.connection.isolation",
				this.hibernateConnectionIsolation);

		return properties;
	}
}
