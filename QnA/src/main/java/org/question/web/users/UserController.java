package org.question.web.users;

import java.util.List;

import javax.validation.Valid;

import org.question.dao.users.UserDao;
import org.question.domain.users.Authenticate;
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
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao; 
	
	@RequestMapping("/signup/form")
	public String signupForm(Model model) {
		model.addAttribute("user", new User());
		return "users/signup/form";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Valid  User user, BindingResult bindingResult) {
		logger.debug("UserInput: {}", user);
		
		if (bindingResult.hasErrors()) {
			logger.debug("BindingResult has ERROR");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				logger.debug("    {}: {}", error.getCode(), error.getDefaultMessage());
			}
			
			return "users/signup/form";
		}
		
		userDao.insert(user);
		logger.debug("ConfirmDB: {}", userDao.selectByUserId(user.getUserId()));
		
		return "redirect:/";
	}
	
	@RequestMapping("/login/form")
	public String loginForm(Model model) {
		model.addAttribute("authenticate", new User());
		return "users/login/form";
	}
	
	@RequestMapping("/login")
	public String login(@Valid Authenticate authenticate, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "users/login/form";
		}
		
		User user = userDao.selectByUserId(authenticate.getUserId());
		if (user == null) {
			// TODO Exception: NO SUCH USER
		}
		
		if (!user.getPassword().equals(authenticate.getPassword())) {
			// TODO Exception: INCORRECT PASSWORD
		}
		
		// TODO Create Session and save user info.
		
		return "/";
	}
	
}
