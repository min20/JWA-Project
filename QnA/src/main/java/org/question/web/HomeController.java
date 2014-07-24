package org.question.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
	//	(webapp)/home.jsp과 연결된다.
		return "home";
	}
	
}
