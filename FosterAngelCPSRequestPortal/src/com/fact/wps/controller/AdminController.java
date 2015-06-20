package com.fact.wps.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fact.services.RequestService;
import com.fact.services.UserService;
import com.fact.wps.model.AdminReport;
import com.fact.wps.model.AdminSummary;
import com.fact.wps.model.CWRequest;
import com.fact.wps.model.Profile;
/**
 * @author sshrotriya
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	
	private RequestService reqService ;
	private UserService uService;
	
	private final static String DATE_FORMAT = "yyyy-MM-dd";

	
// TODO   private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	public AdminController() {
		super();
		reqService = new com.fact.services.RequestService();
		uService = new com.fact.services.UserService();		
	}
/**
 * 
 * @param request
 * @param model
 * @return
 * Gets the Admin sumary page. Check all authentication in controllers. Use services to just set values on models
 */
	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public ModelAndView getAdminSummaryPage(HttpServletRequest request, ModelMap model) 
	{
		AdminSummary adminSummary = new AdminSummary();
		String uid  = null;
	
/*		TODO: Not sure if this is required
		if(request.getSession().getAttribute("signinuser") != null)
		{
			profile = (Profile) request.getSession().getAttribute("signinuser");
			uid = profile.getEmail();
		}
*/
	    
		this.reqService.fillAdminRequestSummary(adminSummary);
		this.uService.fillAdminUserSummary(adminSummary);
			
		model.addAttribute("adminsummary", adminSummary);
				
		return new ModelAndView("adminhome");

	}	
	
	/**
	 * Fetch requests of all status type to display admin request summary page.
	 * 
	 * 
	 */
	@RequestMapping(value = "/reqsummary", method = RequestMethod.GET)
	public ModelAndView getRequestSummaryPage(HttpServletRequest request, ModelMap model) 
	{
		
		Map <String, List<CWRequest>> reqSummaryMap = null;
		List <String> statusTypes = new ArrayList<String> ();
		statusTypes.add("adminpending");
		statusTypes.add("approved");
		statusTypes.add("overdue");
		statusTypes.add("closed");
		statusTypes.add("denied");
		statusTypes.add("delivered");
		reqSummaryMap = this.reqService.getRequestsByStatusTypes(statusTypes, null, null);	
		model.addAttribute("requests", reqSummaryMap);
		model.addAttribute("filter" , "paodce"); // e for Delivered/Executed
		model.addAttribute("searchField" , "");
		model.addAttribute("searchValue" , "");
		String exportToExcel = request.getParameter("exportToExcel");
		if (exportToExcel != null
	                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		 
			return new ModelAndView("adminrequestsummaryexport");
		}
			
		return new ModelAndView("adminrequestsummary");

	}	
	
	/**
	 * Fetch requests of all status type to display admin request summary page.
	 * 
	 * 
	 */
	@RequestMapping(value = "/reqsummary/{filter}/{searchField}/{searchValue}", method = RequestMethod.GET)
	public ModelAndView getRequestSummaryPage(@PathVariable String filter, @PathVariable String searchField, @PathVariable String searchValue, 
			HttpServletRequest request, ModelMap model) 
	{
		
		Map <String, List<CWRequest>> reqSummaryMap = null;
		List <String> statusTypes = new ArrayList<String> ();
		if(filter.contains("p")){
			statusTypes.add("adminpending");
		}
		
		if(filter.contains("a")){
			statusTypes.add("approved");
		}
		
		if(filter.contains("o")){
			statusTypes.add("overdue");
		}
		
		if(filter.contains("c")){
			statusTypes.add("closed");
		}
		
		if(filter.contains("d")){
			statusTypes.add("denied");
		}
		
		if(filter.contains("e")){
			statusTypes.add("delivered");
		}
		
		reqSummaryMap = this.reqService.getRequestsByStatusTypes(statusTypes, searchField, searchValue);	
		model.addAttribute("requests", reqSummaryMap);
		model.addAttribute("filter" , filter);
		model.addAttribute("searchField" , searchField);
		model.addAttribute("searchValue" , searchValue);
		
		String exportToExcel = request.getParameter("exportToExcel");
		if (exportToExcel != null
	                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		 
			return new ModelAndView("adminrequestsummaryexport");
		}
	
		return new ModelAndView("adminrequestsummary");

	}	
	/**
	 * Fetch Admin reports
	 * 
	 * 
	 */
	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public ModelAndView getReportsPage(HttpServletRequest request, ModelMap model) 
	{
		// create a list of reports
		Map <String, List<AdminReport>> allReports = null;
		
		allReports = this.reqService.fillAdminReports(request);
				
		model.addAttribute("reports", allReports);
	
		return new ModelAndView("adminreports");

	}	
	

	/**
	 * Fetch requests of all status type to display admin request summary page.
	 * 
	 * 
	 */
	@RequestMapping(value = "/reqsummary/{filter}", method = RequestMethod.GET)
	public ModelAndView getRequestSummaryPage(@PathVariable String filter,  
			HttpServletRequest request, ModelMap model) 
	{
		
		Map <String, List<CWRequest>> reqSummaryMap = null;
		List <String> statusTypes = new ArrayList<String> ();
		if(filter.contains("p")){
			statusTypes.add("adminpending");
		}
		
		if(filter.contains("a")){
			statusTypes.add("approved");
		}
		
		if(filter.contains("o")){
			statusTypes.add("overdue");
		}
		
		if(filter.contains("c")){
			statusTypes.add("closed");
		}
		
		if(filter.contains("d")){
			statusTypes.add("denied");
		}
		reqSummaryMap = this.reqService.getRequestsByStatusTypes(statusTypes, null, null);	
		model.addAttribute("requests", reqSummaryMap);
		model.addAttribute("filter" , filter);
		model.addAttribute("searchField" , "");
		model.addAttribute("searchValue" , "*");	
		
		String exportToExcel = request.getParameter("exportToExcel");
		if (exportToExcel != null
	                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		 
			return new ModelAndView("adminrequestsummaryexport");
		}
	
		return new ModelAndView("adminrequestsummary");

	}	
	
	
	/**
	 * Fetch requests of all status type to display admin user summary page.
	 * 
	 * 
	 */
	@RequestMapping(value = "/usersummary", method = RequestMethod.GET)
	public ModelAndView getUserSummaryPage(HttpServletRequest request, ModelMap model) 
	{
		AdminController adminController = this;
		Map <String, List<Profile>> reqSummaryMap = null;
		List <String> userTypes = new ArrayList<String> ();
		userTypes.add("cw");
		userTypes.add("ss");
		userTypes.add("fa");
		reqSummaryMap = this.reqService.getRequestsByUserTitles(userTypes, "", "");
		model.addAttribute("requests", reqSummaryMap);
		model.addAttribute("requestService", this.reqService);
		model.addAttribute("filterBy", "csa");
		model.addAttribute("searchField", "l");
		model.addAttribute("searchValue", "*");
	
		return new ModelAndView("adminusersummary");

	}
	
	/**
	 * Fetch requests of all status type to display admin user summary page.
	 * 
	 * 
	 */
	@RequestMapping(value = "/usersummary/{filterBy}/{searchField}/{searchValue}", method = RequestMethod.GET)
	public ModelAndView getUserSummaryPage(@PathVariable String filterBy, @PathVariable  String searchField, 
			@PathVariable String searchValue, HttpServletRequest request, ModelMap model) 
	{
		AdminController adminController = this;
		Map <String, List<Profile>> reqSummaryMap = null;
		List <String> userTypes = new ArrayList<String> ();
		if (filterBy.contains("c")) {
			userTypes.add("cw");
		}
		if (filterBy.contains("s")) {
			userTypes.add("ss");
		}
		if (filterBy.contains("a")) {
			userTypes.add("fa");
		}
		reqSummaryMap = this.reqService.getRequestsByUserTitles(userTypes, searchField, searchValue);
		model.addAttribute("requests", reqSummaryMap);
		model.addAttribute("requestService", this.reqService);
		model.addAttribute("filterBy", filterBy);
		model.addAttribute("searchField", searchField);
		model.addAttribute("searchValue", searchValue);
	
		return new ModelAndView("adminusersummary");
	}
	
	/**
	 * Fetch requests of all status type to display admin user summary page.
	 * 
	 * 
	 */
	@RequestMapping(value = "/usersummary/{filterBy}", method = RequestMethod.GET)
	public ModelAndView getUserSummaryPage(@PathVariable String filterBy, HttpServletRequest request, ModelMap model) 
	{
		AdminController adminController = this;
		Map <String, List<Profile>> reqSummaryMap = null;
		List <String> userTypes = new ArrayList<String> ();
		if (filterBy.contains("c")) {
			userTypes.add("cw");
		}
		if (filterBy.contains("s")) {
			userTypes.add("ss");
		}
		if (filterBy.contains("a")) {
			userTypes.add("fa");
		}
		
		reqSummaryMap = this.reqService.getRequestsByUserTitles(userTypes, null, null);
		model.addAttribute("requests", reqSummaryMap);
		model.addAttribute("requestService", this.reqService);
		model.addAttribute("filterBy", filterBy);
		model.addAttribute("searchField", "l");
		model.addAttribute("searchValue", "*");
	
		return new ModelAndView("adminusersummary");
	}
	
	@RequestMapping(value = "/list/{status}/{period}", method = RequestMethod.GET)
	public ModelAndView getRequestsListing(@PathVariable String status, @PathVariable int period,
			HttpServletRequest request, ModelMap model) {

		List<CWRequest> results = null;
		String viewpage = "redirect:/reqsummary";
		String uid  = null;
		Profile profile = null;
		if(request.getSession().getAttribute("signinuser") != null){
			profile = (Profile) request.getSession().getAttribute("signinuser");
			uid = profile.getEmail();
		}
		results = this.reqService.findRequestsByStatusAndTimePeriod(status, period, false, null, null);

		if(status.equals("pending")){
			viewpage = "adminrequestspending" ;
		} else if(status.equals("approved")){
			viewpage = "adminrequestsapproved" ;
		} else if(status.equals("closed")){
			viewpage = "adminrequestsclosed" ;
		} else if(status.equals("overdue")){
			viewpage = "adminequestsoverdue" ;
		} else if(status.equals("denied")){
			viewpage = "adminrequestsdenied" ;
		} else if(status.equals("delivered")){
			viewpage = "adminrequestsdelivered" ;
		}
		//<TODO> Add more
		
		// Get requests and set into model
		model.addAttribute("requestList", results);
		return new ModelAndView(viewpage);

	}
	
	
	/**
	 * Fetch requests of all status type to display admin user summary page.
	 * 
	 * 
	 */
	@RequestMapping(value = "/userdetails/{key}", method = RequestMethod.GET)
	public String getUserDetailsPage(@PathVariable String key,
			HttpServletRequest request, ModelMap model) {
		
//		Map <String, List<CWRequest>> reqSummaryMap = null;
//		List <String> statusTypes = new ArrayList<String> ();
//		statusTypes.add("pending");
//		statusTypes.add("approved");
//		statusTypes.add("overdue");
//		statusTypes.add("closed");
//		statusTypes.add("denied");
		
		String viewPage = "adminuserdetails";
		UserService uService = new UserService();
		Profile p = uService.getProfileByKey(key);
		model.addAttribute("profile", p);
		
		//reqSummaryMap = this.reqService.getAllRequestsForUserByStatus(key, statusTypes);	

		return viewPage;

	}
	
	/**
	 * Fetch requests of all status type to display admin user summary page.
	 * 
	 * 
	 */
	@RequestMapping(value = "/userdetails/{email}", method = RequestMethod.GET)
	public String getUserDetailsPageUsingEmail(@PathVariable String email,
			HttpServletRequest request, ModelMap model) {		
		
		String viewPage = "adminuserdetails";
		UserService uService = new UserService();
		Profile p = uService.getProfileByEmail(email);
		model.addAttribute("profile", p);	
		return viewPage;

	}


}