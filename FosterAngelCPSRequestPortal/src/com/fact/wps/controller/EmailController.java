package com.fact.wps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fact.services.UserService;

@Controller
@RequestMapping("/email")
public class EmailController {
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getAddProfilePage(ModelMap model) {
		
		System.out.println("%%%%%%%% IN EMAIL TEST %%%%%%%%%%%%");
		// Send a confirmation email
		//new MailSenderService().sendEmail();
		new UserService().sendVerificationToken(null, null);
		
		return "listprofile";

	}


}
