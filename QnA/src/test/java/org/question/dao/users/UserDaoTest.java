package org.question.dao.users;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
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
		User user = new User("leegyeongmin", "1234", "이경민", "min20@neighbor.com");
		userDao.insert(user);
		User selectedUser = userDao.selectByUserId(user.getUserId());
		assertThat(selectedUser, is(user));
	}

}
