package com.xszymo.hibernate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {
	@RequestMapping
	public String start() {
		return "start";
	}

}
