package org.question.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class MyBatisTest {
	private static final Logger logger = LoggerFactory.getLogger(MyBatisTest.class);
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void setup() throws IOException {
		String resource = "mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("database-init.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		
		logger.info("Database initialized");
	}
	
	private DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:~/dev/JavaWebAdv/Database/org.question");
		dataSource.setUsername("root");
		
		return dataSource;
	}
	
	@Test
	public void tutorial() throws Exception {
		// JDK 7.0 부터 try 본문 - finally 리소스 반환(close) 형태를 아래 형태로 대체 가능
		// (단 Closeable 라는 인터페이스를 상속받고 있어야 함)
		try (SqlSession session = sqlSessionFactory.openSession();) {
			User user = session.selectOne("UserMapper.selectByUserId", "min20");
			logger.debug("User: {}", user);
		}
	}

	@Test
	public void insert() throws Exception {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			User user = new User("poppy", "password", "뽀삐", "");
			session.insert("UserMapper.insert", user);
			User actual = session.selectOne("UserMapper.selectByUserId", "poppy");

			assertThat(actual, is(user));
		}
	}
	
}
