/**
 * 
 */
package com.fact.wps.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fact.PMF;
import com.fact.common.RequestStatusTypes;
import com.fact.common.RequestTypes;
import com.fact.services.RequestService;
import com.fact.utils.DateUtil;
import com.fact.wps.model.CPSRequestAttachment;
import com.fact.wps.model.CWRequest;
import com.fact.wps.model.Profile;
import com.fact.wps.model.RequestSummary;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;


/**
 * @author kagarwal
 *
 */
@Controller
@RequestMapping("/cwrequest")
public class CWRequestController {
	private RequestService reqService ;
	
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public CWRequestController() {
		super();
		reqService = new RequestService();
	}


	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView getCreateRequestPage(HttpServletRequest request, ModelMap model) {
		Profile uProfile = null;
		String redirectStr = "request";

		if (request.getSession().getAttribute("signinuser") != null) {
			uProfile = (Profile) request.getSession().getAttribute("signinuser");
		
			if (uProfile.getTitle()!= null && uProfile.getTitle().equalsIgnoreCase("cw")){
				//default home
			} else if (uProfile.getTitle()!= null && uProfile.getTitle().equalsIgnoreCase("ss")) {
				redirectStr = "supportstaffrequest";
			} else if (uProfile.getTitle()!= null && uProfile.getTitle().equalsIgnoreCase("fa")) {
				redirectStr = "adminrequest";
			}
		}
		return new ModelAndView(redirectStr);

	}
	
	
	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public ModelAndView getUserRequestSummaryPage(HttpServletRequest request, ModelMap model) {
		
		RequestSummary reqSummary = null;
		String uid  = null;
		Profile profile = null;
		if(request.getSession().getAttribute("signinuser") != null){
			profile = (Profile) request.getSession().getAttribute("signinuser");
			uid = profile.getEmail();
		}

			reqSummary = this.reqService.getRequestSummaryForUser(uid);		

			
			model.addAttribute("reqsummary", reqSummary);
			

		return new ModelAndView("cpsrequests");

	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request, ModelMap model) {
		Profile uProfile = null;
		String redirectStr = "request_step2";
		String requestorId = null;
		String creatorId = null;
		String requestorFirstName = null;
		String requestorLastName = null;
		int numChildSupported = 1;
		String reqType = request.getParameter("reqType");

		if (request.getSession().getAttribute("signinuser") != null) {
			uProfile = (Profile) request.getSession().getAttribute("signinuser");
		
			if (uProfile.getTitle()!= null && uProfile.getTitle().equalsIgnoreCase("cw")){
				
				requestorId = uProfile.getEmail();
				creatorId = uProfile.getEmail();
				requestorFirstName = uProfile.getFirstName();
				requestorLastName = uProfile.getLastName();
			} else if (uProfile.getTitle()!= null && (uProfile.getTitle().equalsIgnoreCase("ss") || uProfile.getTitle().equalsIgnoreCase("fa"))) {
				redirectStr = "supportstaffrequest_step2";
				
				creatorId = uProfile.getEmail();
				if(reqType != null && reqType.equalsIgnoreCase(RequestTypes.CPS.getLabel())){
					requestorFirstName = (String) request.getParameter("cwFirstName");
					requestorLastName =(String) request.getParameter("cwLastName");
					requestorId = (String) request.getParameter("cwEmail");
				} else {
					requestorFirstName = uProfile.getFirstName();
					requestorLastName = uProfile.getLastName();
					requestorId = uProfile.getEmail();
				}
			}
		}
		String childName = request.getParameter("childname");
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		String pidNumber = request.getParameter("pidNumber");
		String county = request.getParameter("county");
		double requestAmount = 0.0;
		if(request.getParameter("requestAmount") != null){
			requestAmount = Double.parseDouble(request.getParameter("requestAmount"));
		}		String requestDescription = request.getParameter("requestDescription");
		int numUsedFact = 0;
		if(request.getParameter("numUsedFact") != null){
			numUsedFact = Integer.parseInt(request.getParameter("numUsedFact"));
		}
		String requestVendor = request.getParameter("requestVendor");
		
		String requestedDate = (String) request.getParameter("requestDate");
		if(request.getParameter("numChildSupported") != null){
			numChildSupported =  Integer.parseInt(request.getParameter("numChildSupported"));
		}
		
		String category = null;
		if(request.getParameter("category") != null) {
			 category = request.getParameter("category");

		}

		CWRequest r = new CWRequest();
		r.setAge(age);
		r.setChildName(childName);
		r.setCountyInRegion7(county);
		r.setNumberOfTimesUsedFact(numUsedFact);
		r.setGender(gender);
		r.setPid(pidNumber);
		r.setReqestedAmount(requestAmount);
		if(requestedDate != null && !requestedDate.isEmpty()) {
			Date tempDate = null;
			try {
				tempDate = new SimpleDateFormat(DateUtil.DATE_FORMAT).parse(requestedDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				// This may be due to date in mm/dd/yyyy format
				try {
					tempDate = new SimpleDateFormat(DateUtil.DATE_FORMAT_US).parse(requestedDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			r.setRequestedDate(tempDate);
		}
		r.setRequestDescription(requestDescription);
		r.setRequestedVendor(requestVendor);
		r.setCategory(category);
		r.setDate(new Date());
		r.setSampleReqFlag(false);
		if(numChildSupported > 0){
			r.setNumOfChildernSupported(numChildSupported);
		}
		
//		if(request.getSession().getAttribute("signinuser") != null){
//			Profile p = (Profile)request.getSession().getAttribute("signinuser");
//			requestorId = p.getEmail();
//			
//		}
		r.setRequestorID(requestorId);
		r.setCreatorID(creatorId);
		r.setRequestorFirstName(requestorFirstName);
		r.setRequestorLastName(requestorLastName);
		if(!uProfile.getTitle().equalsIgnoreCase("fa")){
			r.setStatus(RequestStatusTypes.PENDINGSUPERVISORAPPROVAL.getLabel());		
		} else {
			r.setStatus(RequestStatusTypes.APPROVED.getLabel());			
		}
		
		if(reqType != null && !reqType.isEmpty()){
			r.setRequestType(reqType);
		}
		
		this.reqService.addRequest(r);
		
		//<TODO> Send a email to supervisor for ack
		if(!uProfile.getTitle().equalsIgnoreCase("fa")){
			this.reqService.sendAcknowledgeMsg(uProfile, r);		
		} else {
			redirectStr = "adminrequest_step2";
		}
		

		return new ModelAndView(redirectStr);

	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String getUpdateRequestPage(HttpServletRequest request, ModelMap model) {
		String viewPage = "adminrequestedit";
		Profile uProfile = null;
		String title = null;
		if (request.getSession().getAttribute("signinuser") != null) {
			uProfile = (Profile) request.getSession().getAttribute("signinuser");
			title = uProfile.getTitle();			
		}
		
		if(title != null && title.equalsIgnoreCase("cw")){
			viewPage = "requestedit";
		}		
		
		return viewPage;

	}
	
	@RequestMapping(value = "/details/{reqkey}", method = RequestMethod.GET)
	public ModelAndView getRequestDeatilPage(@PathVariable String reqkey,
			HttpServletRequest request, ModelMap model) {

		String viewPage = "cpspendingrequestsdetail";
		Profile uProfile = null;
		String title = null;
		CWRequest cwRequest = this.reqService.getRequestDetails(reqkey);
		request.getSession().setAttribute("cwrequestinuse", cwRequest);
		
		// Get Request attachments
		List<CPSRequestAttachment>  attachments = this.reqService.getRequestAttachments(reqkey);		
		request.getSession().setAttribute("cwrequestinuse_attachments", attachments);
		
		if (request.getSession().getAttribute("signinuser") != null) {
			uProfile = (Profile) request.getSession().getAttribute("signinuser");
			title = uProfile.getTitle();			
		}
		
		if(title != null && title.equalsIgnoreCase("cw")){
			if(cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.APPROVED.getLabel())){
				viewPage = "cpsapprovedrequestsdetail";
			} else if (cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.CLOSED.getLabel())){
				viewPage = "cpsclosedrequestsdetail";
			} else if (cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.PENDINGRECEIPTS.getLabel())){
				viewPage = "cpsoverduerequestsdetail";
			} else if (cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.DENIED.getLabel())){
				viewPage = "cpspendingrequestsdetail";
			}  else if (cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel())){
				viewPage = "cpsrequestswithreceiptsdetail";
			}  
			else {
				viewPage = "cpspendingrequestsdetail";
			}
		} else if (title != null && title.equalsIgnoreCase("fa")){
			if(cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.APPROVED.getLabel())){
				viewPage = "adminapprovedrequestsdetail";
			} else if (cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.CLOSED.getLabel())){
				viewPage = "adminclosedrequestsdetail";
			} else if (cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.PENDINGRECEIPTS.getLabel()) ||
					cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel()) ){
				viewPage = "adminoverduerequestsdetail";
			} else if (cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.DENIED.getLabel())){
				viewPage = "adminpendingrequestsdetail";
			} else if (cwRequest.getStatus().equalsIgnoreCase(RequestStatusTypes.DELIVERED.getLabel())){
				viewPage = "admindeliveredrequestsdetail";
			}  
			else {
				viewPage = "adminpendingrequestsdetail";
			}
		} 

		return new ModelAndView(viewPage);

	}
	
	@RequestMapping(value = "/attach/{reqkey}", method = RequestMethod.GET)
	public ModelAndView getFileUploadPage(@PathVariable String reqkey,
			HttpServletRequest request, ModelMap model) {

		String viewPage = "cpsfieluploadpage";

		return new ModelAndView(viewPage);

	}
	
	@RequestMapping(value = "/attach", method = RequestMethod.POST)
	public ModelAndView fileUpload(HttpServletRequest request, ModelMap model) {
		String redirecturl = "/";
		String reqkey = (String) request.getParameter("reqkey");

		 Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
		 Map <String, List<FileInfo>> blobFileInfo = blobstoreService.getFileInfos(request);
		 List<FileInfo> fileInfos = (List<FileInfo>) blobFileInfo.get("fileAttach");
	        BlobKey blobKey = blobs.get("fileAttach");
	        String uid = null;
	        
	        CPSRequestAttachment attachment = null;
	        if (blobKey == null) {
	        	redirecturl = "/";
	        } else {
	        	redirecturl = "redirect:/cwrequest/details/" + reqkey;	        	
	        	attachment = new CPSRequestAttachment();
	        	if(fileInfos != null && fileInfos.size() > 0) {
	        		FileInfo fileInfo = fileInfos.get(0);
	        		attachment.setName(fileInfo.getFilename());
	        	} else {
	        		attachment.setName("NoFileName");
	        	}
	     	    //attachment.setName(imageItem.getName());
	     	    attachment.setFileKey(blobKey.getKeyString());
	     	    attachment.setCwRequestKey(reqkey);
	     	    this.reqService.addAttachment(attachment);	
	     	    // Update status to Receipts available
	     	   Profile profile = null;
	     	   if(request.getSession().getAttribute("signinuser") != null){
	     		   profile = (Profile) request.getSession().getAttribute("signinuser");
	     		   uid = profile.getEmail();
	     	   }
	     	    this.reqService.updateRequestStatus(reqkey, RequestStatusTypes.RECEIPTAVAILABLE, uid);
	        	
//	            //res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
	        }
		
		 // Get the image representation
//	    ServletFileUpload upload = new ServletFileUpload();
//	    FileItemIterator iter = null;
//		try {
//			iter = upload.getItemIterator(request);
//		} catch (FileUploadException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    FileItemStream imageItem = null;
//		try {
//			imageItem = iter.next();
//		} catch (FileUploadException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    InputStream imgStream = null;
//		try {
//			imgStream = imageItem.openStream();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	    // construct our entity objects
////	    Blob imageBlob = null;
//		try {
//			imageBlob = new Blob(IOUtils.toByteArray(imgStream));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	   

//	    // persist image
//	    PersistenceManager pm = PMF.get().getPersistenceManager();
//	    pm.makePersistent(attachment);
//	    pm.close();

	    

		return new ModelAndView(redirecturl);

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, ModelMap model) {
		
		String redirectPage = "redirect:summary";
		int numChildSupported = 1;
		String childName = request.getParameter("childname");
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		String pidNumber = request.getParameter("pidNumber");
		String county = request.getParameter("county");
		double requestAmount = 0.0;
		if(request.getParameter("requestAmount") != null){
			requestAmount = Double.parseDouble(request.getParameter("requestAmount"));
		}
		
		double purchasedAmount = 0.0;
		if(request.getParameter("purchasedAmount") != null){
			purchasedAmount = Double.parseDouble(request.getParameter("purchasedAmount"));
		}
		String requestDescription = request.getParameter("requestDescription");
		int numUsedFact = 0;
		
		if(request.getParameter("numUsedFact") != null){
			numUsedFact = Integer.parseInt(request.getParameter("numUsedFact"));
		}
		
		String requestVendor = request.getParameter("requestVendor");		
		String requestedDate = (String) request.getParameter("requestDate");
		String key = request.getParameter("key");
		String category = request.getParameter("category");
		String adminNotes = request.getParameter("adminnotes");
		String status = request.getParameter("status");
		if(request.getParameter("numChildSupported") != null) {
			numChildSupported =  Integer.parseInt(request.getParameter("numChildSupported"));
		}
		String reqType = request.getParameter("reqType");
		String emailContent = request.getParameter("adminemailnotes");
		String uid = null;
		Profile profile = null;
		if(request.getSession().getAttribute("signinuser") != null){
			profile = (Profile) request.getSession().getAttribute("signinuser");
			uid = profile.getEmail();
		}
		
		String deliveredDate = (String) request.getParameter("deliveredDate");

		
			CWRequest r =  new CWRequest();

			
			r.setAge(age);
			r.setChildName(childName);
			r.setCountyInRegion7(county);
			r.setNumberOfTimesUsedFact(numUsedFact);
			r.setGender(gender);
			r.setPid(pidNumber);
			r.setReqestedAmount(requestAmount);
			r.setPurchasedAmount(purchasedAmount);
			if(requestedDate != null && !requestedDate.isEmpty()) {
				Date tempDate = null;
				try {
					tempDate = new SimpleDateFormat(DateUtil.DATE_FORMAT).parse(requestedDate);
				} catch (ParseException e) {
					// This may be due to date in mm/dd/yyyy format
					try {
						tempDate = new SimpleDateFormat(DateUtil.DATE_FORMAT_US).parse(requestedDate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				r.setRequestedDate(tempDate);
			}		
			
			if(deliveredDate != null && !deliveredDate.isEmpty()) {
				Date tempDate = null;
				try {
					tempDate = new SimpleDateFormat(DateUtil.DATE_FORMAT).parse(deliveredDate);
				} catch (ParseException e) {
					// This may be due to date in mm/dd/yyyy format
					try {
						tempDate = new SimpleDateFormat(DateUtil.DATE_FORMAT_US).parse(deliveredDate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				r.setDeliveredDate(tempDate);
			}		
			
			r.setRequestDescription(requestDescription);
			r.setRequestedVendor(requestVendor);
			r.setLastUpdatedDate(new Date());
			
			//<TODO:> - Status based on flow
			if(status != null && !status.isEmpty()) {
				r.setStatus(status);
			}			
			
			if(request.getParameter("isSampleReq") != null) {
				boolean sampleFlag = Boolean.valueOf((String)request.getParameter("isSampleReq"));
				r.setSampleReqFlag(sampleFlag);

			} else {
				r.setSampleReqFlag(false);
			}
			
			if(category != null && !category.isEmpty()){
				r.setCategory(category);
			}
			
			if(adminNotes != null && !adminNotes.isEmpty()) {
				r.setAdminNotes(adminNotes);
			}
			r.setLastUpdatedUser(uid);
			
			if(reqType != null && !reqType.isEmpty()){
				r.setRequestType(reqType);
			}
			
			if(numChildSupported > 0){
				r.setNumOfChildernSupported(numChildSupported);
			}
			
			if(emailContent != null && !emailContent.equals("")){
				r.setAdminEmailNotes(emailContent);
			}
			
			r = this.reqService.updateRequest(r, key);
			
			// if user is admin and request has been approved, send an email to CW
						
			if(profile != null && profile.getTitle().equalsIgnoreCase("fa")){
				redirectPage = "redirect:/admin/reqsummary";
				
				if(r.getStatus().equals(RequestStatusTypes.APPROVED.getLabel())) {
					this.reqService.sendEmailNotificationToRequestor(r);
				}
			}

		

		// return to list
		return new ModelAndView(redirectPage);

	}
	
	
	@RequestMapping(value = "/delete/{key}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String key,
			HttpServletRequest request, ModelMap model) {

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {

			CWRequest r = pm.getObjectById(CWRequest.class, key);
			pm.deletePersistent(r);

		} finally {
			pm.close();
		}

		// return to list
		return new ModelAndView("redirect:/list/pending");

	}
	
	@RequestMapping(value = "/list/{status}", method = RequestMethod.GET)
	public ModelAndView getRequestsListing(@PathVariable String status,
			HttpServletRequest request, ModelMap model) {

		List<CWRequest> results = null;
		String viewpage = "redirect:/summary";
		String uid  = null;
		Profile profile = null;
		if(request.getSession().getAttribute("signinuser") != null){
			profile = (Profile) request.getSession().getAttribute("signinuser");
			uid = profile.getEmail();
		}
		results = this.reqService.getRequestsForUserByStatus(uid, status);

		if(status.equals("pending")){
			viewpage = "cpsrequestspending" ;
		} else if(status.equals("approved")){
			viewpage = "cpsrequestsapproved" ;
		} else if(status.equals("closed")){
			viewpage = "cpsrequestsclosed" ;
		} else if(status.equals("overdue")){
			viewpage = "cpsrequestsoverdue" ;
		} else if(status.equals("denied")){
			viewpage = "cpsrequestsdenied" ;
		}  else if(status.equals("receipts")){
			viewpage = "cpsrequestswithreceipts" ;
		}
		//<TODO> Add more
		
		// Get requests and set into model
		model.addAttribute("requestList", results);
		return new ModelAndView(viewpage);

	}
	
	@RequestMapping(value = "/svrconf/{reqkey}/{email}", method = RequestMethod.GET)
	public ModelAndView getSuperviosrAckPage(@PathVariable String reqkey, @PathVariable String email, HttpServletRequest request, ModelMap model) {
		CWRequest cwReq = null;
		if(reqkey != null && !reqkey.isEmpty()){
			cwReq = this.reqService.getRequestDetails(reqkey);
			// Get requests and set into model
			model.addAttribute("cwrequest", cwReq);
			model.addAttribute("useremail", email);
		}
		return new ModelAndView("requestsvrackview");

	}
	
	@RequestMapping(value = "/svrconf2/{status}/{reqkey}/{useremail}", method = RequestMethod.GET)
	public String updateSuperviosrAck(@PathVariable String status, @PathVariable String reqkey,  @PathVariable String useremail, HttpServletRequest request, ModelMap model) {
		CWRequest cwReq = null; 
		if(status.equalsIgnoreCase("a")) {
			cwReq = this.reqService.updateRequestStatus(reqkey, RequestStatusTypes.PENDINGFACTAPPROVAL, useremail);
		} else if (status.equalsIgnoreCase("d")){
			cwReq = this.reqService.updateRequestStatus(reqkey, RequestStatusTypes.SUPERVISORDENIED, useremail);
		}
		
		this.reqService.sendEmailNotificationToFact(cwReq);
		
		return "svrack_step2";

	}

	// get all request
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public String listRequest(HttpServletRequest request, ModelMap model) {


			List<CWRequest> results = null;
			String uid  = null;
			Profile profile = null;
			if(request.getSession().getAttribute("signinuser") != null){
				profile = (Profile) request.getSession().getAttribute("signinuser");
				uid = profile.getEmail();
			}

				results = this.reqService.getRequestsForUser(uid);		

				if (results.isEmpty()) {
					model.addAttribute("cwrequestList", null);
				} else {
					model.addAttribute("cwrequestList", results);
				}


			return "listcwrequest";

		}
		
		
		@RequestMapping(value = "/filedl/{fileKey}", method = RequestMethod.GET)
		public ModelAndView fileDownload(@PathVariable String fileKey, HttpServletRequest request, HttpServletResponse response,  ModelMap model) {
			
			if(fileKey == null || fileKey.isEmpty()){
				return null;
			}
			
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			BlobKey blobKey = new BlobKey(fileKey);
            try {
				blobstoreService.serve(blobKey, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@RequestMapping(value = "/filedel/{fileKey}/{reqKey}", method = RequestMethod.GET)
		public ModelAndView fileRemove(@PathVariable String fileKey, @PathVariable String reqKey,
				HttpServletRequest request, ModelMap model) {
			if(fileKey == null || fileKey.isEmpty()){
				return null;
			}
			
			// deleted from CPSRequestAttachement details			
			this.reqService.removeAttachment(fileKey);	

			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			BlobKey blobKey = new BlobKey(fileKey);
            blobstoreService.delete(blobKey);
				
			// Return to request detail page
			String redirecturl = "redirect:/cwrequest/details/" + reqKey;
			return new ModelAndView(redirecturl);
		}
		
}
