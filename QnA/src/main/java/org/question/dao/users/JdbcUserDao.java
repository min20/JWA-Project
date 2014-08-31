package org.question.dao.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class JdbcUserDao extends JdbcDaoSupport implements UserDao {
	private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);
	
	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("database-init.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		
		logger.info("Database initialized");
	}

	/* (non-Javadoc)
	 * @see org.question.dao.users.IUserDao#selectByUserId(java.lang.String)
	 */
	@Override
	public User selectByUserId(String userId) {
		String sql = "SELECT * FROM USERS WHERE userId = ?"; 
		RowMapper<User> rowMapper = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(
						rs.getString("userId"),
						rs.getString("password"),
						rs.getString("name"),
						rs.getString("email"));
			}
		};
		
		try {
			return getJdbcTemplate().queryForObject(sql, rowMapper, userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.question.dao.users.IUserDao#insert(org.question.domain.users.User)
	 */
	@Override
	public void insert(User user) {
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		getJdbcTemplate().update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail()); 
	}

	/* (non-Javadoc)
	 * @see org.question.dao.users.IUserDao#update(org.question.domain.users.User)
	 */
	@Override
	public void update(User user) {
		String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
		getJdbcTemplate().update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}
	
}
