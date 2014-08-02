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
	public void inputWithEmptyUserId() {
		User user = new User("", "password", "name", "email@question.org");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		
		assertThat(constraintViolations.size(), is(2));
		
		for (ConstraintViolation<User> constraintViolation : constraintViolations) {
			logger.debug("Violation Error Message: {}", constraintViolation.getMessage());
		}
	}

}