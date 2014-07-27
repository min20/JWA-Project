package org.question.web.users;

import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping("/users/form")
	public String form() {
		return "users/form";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public String create(User user) {
		log.debug("User: {}", user);
		return "users/form";
	}
	
}
