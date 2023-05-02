package com.developerjini.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.developerjini.domain", "com.developerjini.service", "com.developerjini.task"})
@MapperScan("com.developerjini.mapper")
@PropertySource("classpath:/jdbc.properties")
public class RootConfig {
				
	@Value("${db.driverClassName}")
	private String driverClassName;
	@Value("${db.jdbcUrl}")
	private String jdbcUrl;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	
	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(jdbcUrl);		
		config.setUsername(username);
		config.setPassword(password);
		return config;
	}
	
	
	@Bean
	public DataSource dataSource() throws IOException {
	
//		config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
//		config.setJdbcUrl("jdbc:log4jdbc:mariadb://np.developerjini.com:3306/spring_db");		
////		config.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@//localhost:1521/xe");		
//		config.setUsername("SAMPLE");
//		config.setPassword("1234");
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setTypeAliasesPackage("com.developerjini.domain");
		return bean.getObject();
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() throws IOException {
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public Gson gson() {
		return new Gson();
	}
}
