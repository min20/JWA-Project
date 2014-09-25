package org.question.web.users;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.question.dao.users.UserDao;
import org.question.domain.users.Authenticate;
import org.question.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name="userDao")
	private UserDao userDao;

	@RequestMapping("/signup/form")
	public String signupForm(Model model, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			return "redirect:/";
		}

		model.addAttribute("user", new User());
		return "users/form";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Valid User user, BindingResult bindingResult) {
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

	@RequestMapping("{userId}/form")
	public String updateUserInfoForm(@PathVariable String userId, Model model) {
		if (userId == null) {
			throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
		}

		User user = userDao.selectByUserId(userId);
		model.addAttribute("user", user);

		return "users/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public String updateUserInfo(@Valid User user, BindingResult bindingResult, HttpSession session) {
		String sUserId = (String) session.getAttribute("userId");
		if (sUserId == null) {
			return "redirect:/";
		}
		
		logger.debug("UserInput: {}", user);

		if (bindingResult.hasErrors()) {
			logger.debug("BindingResult has ERROR");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				logger.debug("{}: {}", error.getCode(), error.getDefaultMessage());
			}

			return "users/form";
		}

		userDao.update(user);
		logger.debug("ConfirmDB: {}", userDao.selectByUserId(user.getUserId()));

		return "redirect:/";
	}

	@RequestMapping("/login/form")
	public String loginForm(Model model, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			return "redirect:/";
		}

		model.addAttribute("authenticate", new User());
		return "users/login";
	}

	@RequestMapping("/login")
	public String login(@Valid Authenticate authenticate, BindingResult bindingResult, HttpSession session, Model model) {
		if (bindingResult.hasErrors()) {
			return "users/login";
		}

		User user = userDao.selectByUserId(authenticate.getUserId());
		if (user == null) {
			model.addAttribute("errorMessage", "존재하지 않는 사용자입니다. ID를 확인해주세요.");
			return "users/login";
		}

		if (!user.matchPassword(authenticate)) {
			model.addAttribute("errorMessage", "비밀번호가 틀립니다.");
			return "users/login";
		}

		session.setAttribute("userId", user.getUserId());
		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("userId");

		return "redirect:/";
	}

}
