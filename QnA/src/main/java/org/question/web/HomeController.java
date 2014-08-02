package org.question.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
	//	(webapp)/home.jsp과 연결된다.
	//  Return된 String 값을 이용해서 어느 페이지로 갈 지 결정이 된다.
	//  그냥 "경로" 만 적으면 forward로, "redirect:경로" 를 적으면 redirect로 동작한다.
		return "home";
	}
	
}
