/**
 * 
 */
package com.fact.wps.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fact.services.UserService;
import com.fact.wps.model.Profile;

/**
 * @author kagarwal
 *
 */

@Controller
@RequestMapping("/")
public class SignInController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getSignInPage(ModelMap model) {

		return new ModelAndView("signin", model);
	}
	
	
	@RequestMapping(value = "/744646", method = RequestMethod.POST)
	public ModelAndView signin(HttpServletRequest request, ModelMap model) {		
		
		String uName = request.getParameter("uID");
		String uPwd = request.getParameter("uPwd");	
	
		Profile uProfile = new UserService().validateSignIn(uName, uPwd);
		
		if(uProfile != null) {
			model.addAttribute("isValidSignIn", "1");
			request.getSession().setAttribute("signinuser", uProfile);
			return new ModelAndView("redirect:cpshome", model);	
		} else {
			model.addAttribute("isValidSignIn", "0");
			request.getSession().setAttribute("isValidSignIn", "0");
			return new ModelAndView("redirect:/", model);
		}
	}
	
	@RequestMapping(value = "/cpshome", method = RequestMethod.GET)
	public ModelAndView getCPSHome(HttpServletRequest request, ModelMap model) {		
		
		Profile uProfile = null;
		String redirectStr = "cpshome";
		redirectStr = "redirect:cwrequest/summary";
		request.getSession().removeAttribute("isValidSignIn");
		if (request.getSession().getAttribute("signinuser") != null) {
			uProfile = (Profile) request.getSession().getAttribute("signinuser");
		
			if (uProfile.getTitle()!= null && uProfile.getTitle().equalsIgnoreCase("cw")){
				//default home
				redirectStr = "redirect:cwrequest/summary";
			} else if (uProfile.getTitle()!= null && uProfile.getTitle().equalsIgnoreCase("ss")) {
				redirectStr = "supportstaffhome";
			} else if (uProfile.getTitle()!= null && uProfile.getTitle().equalsIgnoreCase("fa")) {
				// default home for Admin
				redirectStr = "redirect:admin/summary";
			} 
			
		}
		return new ModelAndView(redirectStr);
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public ModelAndView signout(HttpServletRequest request, ModelMap model) {		
		
		request.getSession().invalidate();
				
		return new ModelAndView("redirect:/");
		
	}

}
