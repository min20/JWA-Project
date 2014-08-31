package org.question.dao.users;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class MyBatisUserDao implements UserDao {
	private static final Logger logger = LoggerFactory.getLogger(MyBatisUserDao.class);
	
	private SqlSession sqlSession;
	private DataSource dataSource;
	
	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("database-init.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
		
		logger.info("Database initialized");
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public User selectByUserId(String userId) {
		return sqlSession.selectOne("UserMapper.selectByUserId", userId);
	}

	@Override
	public void insert(User user) {
		sqlSession.insert("UserMapper.insert", user);
	}

	@Override
	public void update(User user) {
		sqlSession.update("UserMapper.update", user);
	}

}
