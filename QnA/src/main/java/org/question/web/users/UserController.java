package org.question.web.users;

import java.util.List;

import javax.validation.Valid;

import org.question.dao.users.UserDao;
import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
	public String create(@Valid  User user, BindingResult bindingResult) {
		logger.debug("UserInput: {}", user);
		
		if (bindingResult.hasErrors()) {
			logger.debug("BindingResult has ERROR");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				logger.debug("    {}: {}", error.getCode(), error.getDefaultMessage());
			}
			
			return "users/form";
		}
		
		userDao.insert(user);
		logger.debug("ConfirmDB: {}", userDao.selectByUserId(user.getUserId()));
		
		return "redirect:/";
	}
	
}
