package com.xszymo.hibernate.controllers.standard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Start {
	@RequestMapping
	public String start() {
		return "chat";
	}

}
