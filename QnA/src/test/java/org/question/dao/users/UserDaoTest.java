package org.question.dao.users;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class UserDaoTest {
	private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void selectById() {
		User user = userDao.selectByUserId("min20");
		logger.debug("User: {}", user);
	}
	
	@Test
	public void insert() {
		User user = new User("leegyeongmin1234", "1234", "이경민", "min20@neighbor.com");
		userDao.insert(user);
		User selectedUser = userDao.selectByUserId(user.getUserId());
		assertThat(selectedUser, is(user));
	}
	
	@Test
	public void testUserDaoWithTimeLogger() {
		UserDao daoWithTimeLogger = (UserDao)Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class[] {UserDao.class},
				new UserDaoWithTimeLogger(userDao));
		
		User user = new User("leegyeongmin1234", "1234", "이경민", "min20@neighbor.com");
		daoWithTimeLogger.insert(user);
		User selectedUser = daoWithTimeLogger.selectByUserId(user.getUserId());
		assertThat(selectedUser, is(user));
	}

}
