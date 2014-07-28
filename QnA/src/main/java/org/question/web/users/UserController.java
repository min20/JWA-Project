package org.question.web.users;

import org.question.dao.users.UserDao;
import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao; 
	
	@RequestMapping("/users/form")
	public String form(Model model) {
		model.addAttribute(new User());
		return "users/form";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public String create(User user) {
		logger.debug("UserInput: {}", user);
		
		userDao.insert(user);
		logger.debug("ConfirmDB: {}", userDao.selectByUserId(user.getUserId()));
		
		return "users/form";
	}
	
}
