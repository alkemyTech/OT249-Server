package com.alkemy.ong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerController {

	@GetMapping("/api/docs")
	public String apiControl() {
		
		return "redirect:/swagger-ui.html";
		
	}
	
}
