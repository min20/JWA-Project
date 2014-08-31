package org.question.domain.users;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTest {
	private static Validator validator;
	private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void userWithEmptyUserId() {
		User user = new User("", "password", "name", "email@question.org");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		
		// 원래 userId가 공백일 경우 NotEmpty와 Size(Max)로 인해 두 가지 예외처리가 발생했다.
		// 이후 NotNull과 Size(Max)로 변경함에 따라 예외가 한 가지인 것으로 코드를 수정한다.
		//assertThat(constraintViolations.size(), is(2));
		assertThat(constraintViolations.size(), is(1));
		
		for (ConstraintViolation<User> constraintViolation : constraintViolations) {
			logger.debug("Violation Error Message: {}", constraintViolation.getMessage());
		}
	}
	
	@Test
	public void matchPassword() throws Exception {
		String userId = "inputUserId";
		String password = "inputPassword";
		
		Authenticate authenticate = new Authenticate(userId, password);
		User user = new User(userId, password, "inputName", "");
		boolean isMatched = user.matchPassword(authenticate);
		assertTrue(isMatched);
		
		password = "incorrectPassword";
		authenticate.setPassword(password);
		isMatched = user.matchPassword(authenticate);
		assertFalse(isMatched);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updateWhenMismatchUserId() throws Exception {
		User user = new User("userId", "password", "inputName", "");
		User updateUser = new User("updateUserId", "password", "updateName", "min20@nhnnext.org");
		user.update(updateUser);
	}
	
	@Test
	public void update() throws Exception {
		User user = new User("userId", "password", "inputName", "");
		User updateUser = new User("userId", "password", "updateName", "min20@nhnnext.org");
		User updatedUser = user.update(updateUser);
		
		assertThat(updatedUser, is(updateUser));
	}

}
