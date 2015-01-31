package com.fact.wps.controller;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fact.PMF;
import com.fact.common.ProfileStatusTypes;
import com.fact.services.TokenGeneratorService;
import com.fact.services.UserService;
import com.fact.wps.model.Profile;
import com.fact.wps.model.VerificationToken;
import com.google.appengine.api.datastore.KeyFactory;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddProfilePage(ModelMap model) {

		return "signup";

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request, ModelMap model) {		
		String nextPage = "signup_step2";

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String pname = request.getParameter("pname");
		String email = request.getParameter("email");
		String supervisorFirstName = request.getParameter("svrfirstname");
		String supervisorLastName = request.getParameter("svrlastname");
		String unitNum = request.getParameter("unumber");
		String title = request.getParameter("title");
		String wphone = request.getParameter("wphone");
		String mphone = request.getParameter("mphone");
		String supervisorEmail = request.getParameter("svrEmail");
		String supervisorPhone = request.getParameter("svrPhone");
		String userPwd = request.getParameter("uPassword");
		boolean firstTimeFACTUser = Boolean.valueOf((String)request.getParameter("usedfa"));

		Profile p = new Profile();
		p.setCellPhone(mphone);;
		p.setEmail(email.toLowerCase());
		p.setFirstName(fname);
		p.setLastName(lname);
		p.setPreferedName(pname);
		p.setuPassword(userPwd);
		p.setSupervisorFirstName(supervisorFirstName);
		p.setSupervisorLastName(supervisorLastName);
		p.setSupervisorEmail(supervisorEmail);
		p.setSupervisorPhone(supervisorPhone);
		if(title.isEmpty()){
			title = "cw";
		}
		p.setTitle(title);
		p.setWorkPhone(wphone); 
		p.setUnitNumber(unitNum);
		p.setFirstTimeFactUser(firstTimeFACTUser);
		p.setProfileStatus(ProfileStatusTypes.NEW);
		p.setDate(new Date());

		UserService uService = new UserService();
		//<TODO> Validate for uniqueness
		if (uService.isUserIdVailable(p) == true ){
			uService.addUser(p);
			VerificationToken token = new VerificationToken();
			token.setUserEmail(p.getEmail());
			token.setToken(TokenGeneratorService.generateToken());
			token.setTimeStamp(System.currentTimeMillis());
			token.setActive(true);
			if (uService.sendVerificationToken(p, token)){
			//	Persist token value for future user
				new TokenGeneratorService().addToken(token);
			} else {
				//Some issue, need to re-enter the profile
			}
		} else {
			//Error Page or profile page for edit
			model.addAttribute("isUserIDAvailable", false);
			model.addAttribute("profile", p);
			nextPage = "signup";
		}		

		return new ModelAndView(nextPage);

	}

	@RequestMapping(value = "/view/{key}", method = RequestMethod.GET)
	public String getProfileViewPage(@PathVariable String key,
			HttpServletRequest request, ModelMap model) {
		    if(request.getSession().getAttribute("passwdFlag") != null)
		        request.getSession().removeAttribute("passwdFlag");
			String viewPage = "adminprofileview";
			UserService uService = new UserService();
			Profile p = uService.getProfileByKey(key);
			model.addAttribute("profile", p);
			if(p.getTitle() != null && !p.getTitle().isEmpty() && !p.getTitle().equalsIgnoreCase("fa")){
				viewPage = "cpsprofileview";
			}

		return viewPage;

	}
	
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String getProfileVerificationPage(HttpServletRequest request, ModelMap model) {			

		return "signup_step2";

	}
	
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ModelAndView signinVerification(HttpServletRequest request, ModelMap model) {		
		
		boolean isValidCode = false;
		String verificationCode = request.getParameter("verificationcode");
		String userid = request.getParameter("uID");
		isValidCode = new TokenGeneratorService().validateTokenByUserID(userid, verificationCode);
		
		if(isValidCode) {
			// Updated profile status to Active
			model.addAttribute("isVerified", true);
			UserService uService = new UserService();
			uService.updateUserStatus(userid, ProfileStatusTypes.ACTIVE);
		} else {
			// Send error page to user.
			model.addAttribute("isVerified", false);
		}
		
		return new ModelAndView("signup_step3");
		
	}
	
	@RequestMapping(value = "/edit/{key}", method = RequestMethod.GET)
	public ModelAndView getProfileEditPage(@PathVariable String key,
			HttpServletRequest request, ModelMap model) {
		String viewPage = "adminprofileedit";			
		Profile p = null;
	//	if (request.getSession().getAttribute("signinuser") == null ){
			UserService uService = new UserService();
			p = uService.getProfileByKey(key);
			//request.getSession().setAttribute("signinuser", p);
	//	} else {
		//	p = (Profile) request.getSession().getAttribute("signinuser") ;
		//}

		if(p.getTitle() != null && !p.getTitle().isEmpty() && !p.getTitle().equalsIgnoreCase("fa")){
			viewPage = "cpsprofileedit";
		}
		model.addAttribute("profile", p);
		return new ModelAndView(viewPage);
		
	}


	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, ModelMap model) {

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String pname = request.getParameter("pname");
		//String email = request.getParameter("email");
		String supervisorFirstName = request.getParameter("svrfirstname");
		String supervisorLastName = request.getParameter("svrlastname");
		String unitNum = request.getParameter("unumber");
		String wphone = request.getParameter("wphone");
		String mphone = request.getParameter("mphone");
		String supervisorEmail = request.getParameter("svrEmail");
		String supervisorPhone = request.getParameter("svrPhone");
		String userPwd = request.getParameter("uPassword");
		String olduserPwd = request.getParameter("uOldPassword");
		String key = request.getParameter("key");
		//System.out.println("USERPASSWD "+olduserPwd);
		
		boolean isProfileForSignInUser = false;
		Profile signInUser = (Profile) request.getSession().getAttribute("signinuser");		
		if(KeyFactory.keyToString(signInUser.getKey()).equalsIgnoreCase(key)) {
			isProfileForSignInUser = true;	
		}
		
		boolean isValidCurrentPwd = false;
		if(isProfileForSignInUser)		{
		
			isValidCurrentPwd = new UserService().validateSignInUsingKey(key, olduserPwd);
		}
		
		Profile p = new Profile();
		p.setCellPhone(mphone);;
		//p.setEmail(email);
		p.setFirstName(fname);
		p.setLastName(lname);
		p.setPreferedName(pname);
		if(userPwd != null && !userPwd.isEmpty()){
			p.setuPassword(userPwd);
		}
		
		if(supervisorFirstName != null && !supervisorFirstName.isEmpty())
		p.setSupervisorFirstName(supervisorFirstName);
		
		if(supervisorLastName != null && !supervisorLastName.isEmpty())
		p.setSupervisorLastName(supervisorLastName);
		
		if(supervisorEmail != null && !supervisorEmail.isEmpty())
		p.setSupervisorEmail(supervisorEmail);
		
		if(supervisorPhone != null && !supervisorPhone.isEmpty())
		p.setSupervisorPhone(supervisorPhone);
		
		p.setWorkPhone(wphone);
		p.setUnitNumber(unitNum);
		p.setLastUpdatedDate(new Date());
		
			
		if(isProfileForSignInUser && !isValidCurrentPwd)
		{
			request.getSession().setAttribute("passwdFlag", true);
			return new ModelAndView("redirect:/profile/edit/"+key);
		}
		else
		{
			Profile updatedProfile = new UserService().updateUser(p, key);
			if(isProfileForSignInUser) {
				request.getSession().setAttribute("signinuser", updatedProfile);
			}
			return new ModelAndView("redirect:/cpshome");
		}

	}

	@RequestMapping(value = "/delete/{key}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String key,
			HttpServletRequest request, ModelMap model) {

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {

			Profile p = pm.getObjectById(Profile.class, key);
			pm.deletePersistent(p);

		} finally {
			pm.close();
		}

		// return to list
		return new ModelAndView("redirect:../list");

	}

	// get all customers
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listProfile(ModelMap model) {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Profile.class);
		q.setOrdering("date desc");

		List<Profile> results = null;

		try {
			results = (List<Profile>) q.execute();

			if (results.isEmpty()) {
				model.addAttribute("profileList", null);
			} else {
				model.addAttribute("profileList", results);
			}

		} finally {
			q.closeAll();
			pm.close();
		}

		return "listprofile";

	}

}