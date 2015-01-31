/**
 * 
 */
package com.fact.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.fact.FACTAppProperties;
import com.fact.PMF;
import com.fact.common.EmailTemplateTypes;
import com.fact.common.ReportTypes;
import com.fact.common.RequestStatusTypes;
import com.fact.utils.DateUtil;
import com.fact.wps.model.AdminReport;
import com.fact.wps.model.AdminSummary;
import com.fact.wps.model.CPSRequestAttachment;
import com.fact.wps.model.CWRequest;
import com.fact.wps.model.Profile;
import com.fact.wps.model.RequestSummary;
import com.fact.wps.model.SequenceNumber;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author kagarwal
 *
 */
public class RequestService {
	
	/**
	 * Add a new request
	 * @param cwReq
	 */
	public void addRequest(CWRequest cwReq) {
		if(cwReq != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				cwReq.setRequestNumber(this.getNextRequestNumber());
				pm.makePersistent(cwReq);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				pm.close();
			}
		}
	}
	
	/**
	 * Update request
	 * @param cwReq
	 * @param key
	 */
	public CWRequest updateRequest(CWRequest cwReq, Object key) {
		CWRequest cwr = null;
		if(cwReq != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {

				cwr = pm.getObjectById(CWRequest.class, key);			
				cwr.setAdminNotes(cwReq.getAdminNotes());
				cwr.setAge(cwReq.getAge());
				cwr.setChildName(cwReq.getChildName());
				cwr.setCountyInRegion7(cwReq.getCountyInRegion7());
				cwr.setNumberOfTimesUsedFact(cwReq.getNumberOfTimesUsedFact());
				cwr.setGender(cwReq.getGender());
				cwr.setPid(cwReq.getPid());
				cwr.setReqestedAmount(cwReq.getReqestedAmount());
				cwr.setRequestedDate(cwReq.getRequestedDate());
				cwr.setRequestDescription(cwReq.getRequestDescription());
				cwr.setRequestedVendor(cwReq.getRequestedVendor());
				if(cwReq.getStatus() != null){
					cwr.setStatus(cwReq.getStatus());
				}
				cwr.setSampleReqFlag(cwReq.isSampleReqFlag());
				if(cwReq.getCategory() != null && !cwReq.getCategory().isEmpty()){
				cwr.setCategory(cwReq.getCategory());
				}
				
				if(cwReq.getAdminNotes() != null && !cwReq.getAdminNotes().isEmpty()){
					cwr.setAdminNotes(cwReq.getAdminNotes());
				}
				cwr.setLastUpdatedDate(cwReq.getLastUpdatedDate());
				cwr.setLastUpdatedUser(cwReq.getLastUpdatedUser());
				cwr.setNumOfChildernSupported(cwReq.getNumOfChildernSupported());
				cwr.setRequestType(cwReq.getRequestType());
				cwr.setAdminEmailNotes(cwReq.getAdminEmailNotes());
				cwr.setPurchasedAmount(cwReq.getPurchasedAmount());
				cwr = pm.detachCopy(cwr);
			} finally {

				pm.close();
			}

		}
		return cwr;
	}

	/**
	 * Updates request status
	 * @param reqKey
	 * @param RequestStatusTypes
	 * @param userEmail
	 */
	public CWRequest updateRequestStatus(String reqKey, RequestStatusTypes status, String userEmail) {
		CWRequest cwReq = null;
		if(reqKey != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				cwReq  = pm.getObjectById(CWRequest.class, reqKey);
				if(status != null) {
				cwReq.setStatus(status.getLabel());
				cwReq.setLastUpdatedUser(userEmail);
				cwReq.setLastUpdatedDate(new Date());
				cwReq = pm.detachCopy(cwReq);
				}
			} finally {
				pm.close();
			}

		}
		return cwReq;
	}
	@SuppressWarnings("unchecked")
	/*
	 * Return list of all request from a user 
	 * @param uid
	 *  
	 */
	public List<CWRequest> getRequestsForUser(String uid){
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(CWRequest.class);
		q.setFilter("requestorID == uid");		
		q.declareParameters("String uid");
		q.setOrdering("date desc");

		List<CWRequest> results = null;

		try {
			results = (List<CWRequest>) q.execute(uid);
			
			if(results == null){
				results = new ArrayList<CWRequest>();
			} else {
				results =  (List<CWRequest>) pm.detachCopyAll(results);
			}

		} finally {
			q.closeAll();
			pm.close();
		}		

		return results;
	}
	
	@SuppressWarnings("unchecked")
	/*
	 * Return list of all request (for Admin) // TODO: need to filter else performance problem
	 * @param uid
	 *  
	 */
	public List<CWRequest> getAllRequests()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(CWRequest.class);
//		q.setFilter("requestorID == uid");		
//		q.declareParameters("String uid");
		q.setOrdering("date desc");

		List<CWRequest> results = null;

		try {
			results = (List<CWRequest>) q.execute();
			
			if(results == null){
				results = new ArrayList<CWRequest>();
			} else {
				results =  (List<CWRequest>) pm.detachCopyAll(results);
			}

		} finally {
			q.closeAll();
			pm.close();
		}		

		return results;
	}
	
	
	@SuppressWarnings("unchecked")
	/*
	 * Return list of all request from a user 
	 * @param uid
	 *  
	 */
	public List<CWRequest> getRequestsForUserByStatus(String uid, String status){
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		

		String [] sts = null;
		if(!status.isEmpty() && status.equals("pending")){
			sts = new String [] {RequestStatusTypes.PENDINGFACTAPPROVAL.getLabel(), RequestStatusTypes.PENDINGSUPERVISORAPPROVAL.getLabel()};
		} else if (status.equals("approved")){
			sts = new String []{RequestStatusTypes.APPROVED.getLabel()};
		} else if (status.equals("denied")){
			sts = new String []{RequestStatusTypes.DENIED.getLabel(), RequestStatusTypes.SUPERVISORDENIED.getLabel()};
		} else if (status.equals("overdue")){
			sts = new String []{RequestStatusTypes.PENDINGRECEIPTS.getLabel()};
		} else if (status.equals("closed")){
			sts = new String []{RequestStatusTypes.CLOSED.getLabel()};
		} else if (status.equals("receipts")){
			sts = new String []{RequestStatusTypes.RECEIPTAVAILABLE.getLabel()};
		} 
		
		
		StringBuffer qStr = new StringBuffer("SELECT from com.fact.wps.model.CWRequest where requestorID == \'").append(uid).append("\'").append(" && :r1.contains(status)");

		

		Query q = pm.newQuery(qStr.toString());
		List<CWRequest> results = null;

		try {
			results = (List<CWRequest>) q.execute(Arrays.asList(sts));
			
			if(results == null){
				results = new ArrayList<CWRequest>();
			} else {
				results =  (List<CWRequest>) pm.detachCopyAll(results);
			}

		} finally {
			q.closeAll();
			pm.close();
		}		

		return results;
	}
	
	
	@SuppressWarnings("unchecked")
	/*
	 * Return map of all request for a user 
	 * @param uid
	 *  
	 */
	public Map<String, List<CWRequest>> getAllRequestsForUserByStatus(String user,
		List<String> statusTypes) {

	Map <String, List<CWRequest>> reqSummaryMap = new HashMap<String, List<CWRequest>>();		

	for (int i=0; i < statusTypes.size(); i++) {
		String statusType = statusTypes.get(i);
		reqSummaryMap.put(statusType, this.getRequestsForUserByStatus(user, statusType));
	}
	
	return reqSummaryMap; 
}
	
	
	/*
	 * Return request details
	 * @param reqkey
	 *  
	 */
	public CWRequest getRequestDetails(String reqkey){
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();

		CWRequest cwr = null;

		try {
			cwr = pm.getObjectById(CWRequest.class, reqkey);				

		} finally {
			pm.close();
		}		

		return cwr;
	}
	
	
	/**
	 * Generates requests summary 
	 * @param uId
	 * @return
	 */
	public RequestSummary getRequestSummaryForUser(String uId){
		RequestSummary reqSummary = new RequestSummary();
		List<CWRequest> results = null;
		
		results = this.getRequestsForUser(uId);
		int approvedReqCount =0 ;
		int pendingReqCount = 0;
		int closedReqCount = 0;
		int pendingReceiptsCount = 0;
		int errorReqCount = 0;		
		int deniedReqCount = 0;
		int reqWithReceipts = 0;
		
		
		for (CWRequest req : results){
			if(req.getStatus() != null) {
			
			if(req.getStatus().equalsIgnoreCase(RequestStatusTypes.APPROVED.getLabel())){
				approvedReqCount += 1;
			} else if (req.getStatus().equalsIgnoreCase(RequestStatusTypes.CLOSED.getLabel())){
				closedReqCount += 1;
				
			} else if (req.getStatus().equalsIgnoreCase(RequestStatusTypes.PENDINGRECEIPTS.getLabel())
					){
				pendingReceiptsCount += 1;
			} else if (req.getStatus().equalsIgnoreCase(RequestStatusTypes.RECEIPTAVAILABLE.getLabel())
					){
				reqWithReceipts += 1;
			}  
			else if (req.getStatus().equalsIgnoreCase(RequestStatusTypes.PENDINGFACTAPPROVAL.getLabel()) || req.getStatus().equalsIgnoreCase(RequestStatusTypes.PENDINGSUPERVISORAPPROVAL.getLabel()) 
					|| req.getStatus().equalsIgnoreCase(RequestStatusTypes.NEW.getLabel())){
				pendingReqCount += 1;
			} else if(req.getStatus().equalsIgnoreCase(RequestStatusTypes.DENIED.getLabel()) || req.getStatus().equalsIgnoreCase(RequestStatusTypes.SUPERVISORDENIED.getLabel()) ) {
				deniedReqCount  += 1;
			}
			}
		}
		
		reqSummary.setApprovedRequest(approvedReqCount);
		reqSummary.setClosedRequest(closedReqCount);
		reqSummary.setOverdueRequest(pendingReceiptsCount);
		reqSummary.setPendingRequest(pendingReqCount);
		reqSummary.setRequestInError(errorReqCount);		
		reqSummary.setRequestInError(deniedReqCount);
		reqSummary.setReceiptAvailableRequest(reqWithReceipts);
		
		return reqSummary;
		
	}

	/**
	 * Generates requests related summary for Admin. Common for all Admin
	 * @param adminSummary.
	 * @return
	 * TODO: Need to set exceptions
	 */
	public void fillAdminRequestSummary(AdminSummary adminSummary)
	{
		List<CWRequest> results = null;
		
// get All request related information
		results = this.getAllRequests();  // Gets all the requests

		int newReqCount = 0;
		int overdueCount = 0; // PENDINGRECEIPT
//		int errorReqCount = 0;


		List <String> statusTypes = new ArrayList<String> ();
		Map <String, List<CWRequest>> reqSummaryMap = null;
		
		statusTypes.add("adminpending");
		statusTypes.add("overdue");
	    reqSummaryMap = getRequestsByStatusTypes(statusTypes, null, null);
	    
	    results = reqSummaryMap.get("adminpending");
	    if (results != null)
	    	newReqCount = results.size();
	    
	    results = reqSummaryMap.get("overdue");
	    if (results != null)
	    	overdueCount = results.size();
		
// set on MODEL and return
		if (adminSummary == null)
			adminSummary = new AdminSummary();
		adminSummary.setNewRequests(newReqCount);
		adminSummary.setOverdueRequests(overdueCount);
		
		return;
	}
	
   /*
  * to request
	 * @param attachment
	 */
	public void addAttachment(CPSRequestAttachment attachment) {
		if(attachment != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(attachment);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				pm.close();
			}
		}
	}
	
	/**
	 * Create a email to CPS worker's supervisor for request ack.
	 * @param profile
	 * @param cwRequest
	 * @return
	 */
	public boolean sendAcknowledgeMsg(Profile profile, CWRequest cwRequest) {
		// TODO implement method!
		// Use MailSenderService to Send the mail. (Make sure that the mail contains a new link w/Profile info)
		// Add the token to DB
		// Return True if process is successful.
		
		String toEmail = profile.getSupervisorEmail();
		String fromEmail = FACTAppProperties.FACT_ADMIN_EMAIL;
		String subject = "FOSTER ANGELS:Approval Needed";
		String ccEmail = FACTAppProperties.FACT_REQUEST_EMAIL_CC;
		MailSenderService mailServ = new MailSenderService();
		MimeMessage msg = mailServ.createMessage(); 
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false)); 

			if (ccEmail.length() > 0) {
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
			}

			msg.setSubject(subject);
			Map<String, String> msgData = new HashMap<String, String>();
			msgData.put("svrname", profile.getSupervisorFirstName());
			msgData.put("cwfname", profile.getFirstName());
			msgData.put("cwlname", profile.getLastName());
			msgData.put("childname", cwRequest.getChildName());
			msgData.put("svremail", profile.getSupervisorEmail());
			msgData.put("reqkey", KeyFactory.keyToString(cwRequest.getKey()));	
			
			String msgTxt = mailServ.getEmailMessage(EmailTemplateTypes.REQSUPERVISORVERIFICATION, msgData);
			msg.setText(msgTxt, "utf-8");
			msg.setSentDate(new Date());
			mailServ.sendEmail(msg);
			return true;

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			//System.out.println("Email sent!");
		}
		return false;
		
	}
	
	/**
	 * Notify FACT admin about the new request
	 * @param profile
	 * @param cwRequest
	 * @return
	 */
	public boolean sendEmailNotificationToFact(CWRequest cwRequest) {
		// TODO implement method!
		// Use MailSenderService to Send the mail. (Make sure that the mail contains a new link w/Profile info)
		// Add the token to DB
		// Return True if process is successful.
		
		String toEmail = FACTAppProperties.FACT_ADMIN_EMAIL;; 
		String fromEmail = FACTAppProperties.FACT_ADMIN_EMAIL;;
		String subject = "New Request Notification";
		MailSenderService mailServ = new MailSenderService();
		MimeMessage msg = mailServ.createMessage(); 
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false)); 

			msg.setSubject(subject);
			Map<String, String> msgData = new HashMap<String, String>();
			msgData.put("childname", cwRequest.getChildName());
			msgData.put("reqkey", KeyFactory.keyToString(cwRequest.getKey()));			
			String msgTxt = mailServ.getEmailMessage(EmailTemplateTypes.REQFACTNOIFCATION, msgData);
			msg.setText(msgTxt, "utf-8");
			msg.setSentDate(new Date());

			mailServ.sendEmail(msg);
			return true;

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			//System.out.println("Email sent!");
		}
		return false;
		
	}
	
	@SuppressWarnings("unchecked")
	
	/**
	 * Notify requestor about the request status
	 * @param profile
	 * @param cwRequest
	 * @return
	 */
	public boolean sendEmailNotificationToRequestor(CWRequest cwRequest) {
		// TODO implement method!
		// Use MailSenderService to Send the mail. (Make sure that the mail contains a new link w/Profile info)
		// Add the token to DB
		// Return True if process is successful.
		
		String toEmail = cwRequest.getRequestorID(); 
		String fromEmail = FACTAppProperties.FACT_ADMIN_EMAIL;;
		String subject = "FOSTER ANGELS:Request Approved";
		MailSenderService mailServ = new MailSenderService();
		MimeMessage msg = mailServ.createMessage(); 
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false)); 

			msg.setSubject(subject);
			Map<String, String> msgData = new HashMap<String, String>();
			msgData.put("fname", cwRequest.getRequestorFirstName());
			
			msgData.put("reqNum", String.valueOf(cwRequest.getRequestNumber()));			
			msgData.put("childName", cwRequest.getChildName());
			msgData.put("emailcontent", cwRequest.getAdminEmailNotes());
			String msgTxt = mailServ.getEmailMessage(EmailTemplateTypes.REQSTATUSUPDATE, msgData);
			msg.setText(msgTxt, "utf-8");
			msg.setSentDate(new Date());

			mailServ.sendEmail(msg);
			return true;

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			//System.out.println("Email sent!");
		}
		return false;
		
	}
	
	@SuppressWarnings("unchecked")
	/*
	 * Return list of all request based on status types 
	 * @param uid
	 *  
	 */
	public Map<String, List<CWRequest>> getRequestsByStatusTypes(List<String> statusTypes, String searchField, String searchValue){
		
		Map <String, List<CWRequest>> reqSummaryMap = new HashMap<String, List<CWRequest>>();		

		if(!statusTypes.isEmpty() && statusTypes.contains("pending")){
			reqSummaryMap.put("pending", this.findRequestsByStatusAndTimePeriod("pending", 0, true, searchField, searchValue));
		} 
		if (statusTypes.contains("approved")){
			reqSummaryMap.put("approved", this.findRequestsByStatusAndTimePeriod("approved", 0, true , searchField, searchValue));
		} 
		if (statusTypes.contains("denied")){
			reqSummaryMap.put("denied", this.findRequestsByStatusAndTimePeriod("denied", -90, true , searchField, searchValue));
		} 
		if (statusTypes.contains("overdue")){
			reqSummaryMap.put("overdue", this.findRequestsByStatusAndTimePeriod("overdue", 0, true , searchField, searchValue));
		} 
		if (statusTypes.contains("closed")){
			reqSummaryMap.put("closed", this.findRequestsByStatusAndTimePeriod("closed",-60, true , searchField, searchValue));
		}
		if (statusTypes.contains("new")){
			reqSummaryMap.put("closed", this.findRequestsByStatusAndTimePeriod("new",0, true , searchField, searchValue));
		}
		if (statusTypes.contains("adminpending")){
			reqSummaryMap.put("adminpending", this.findRequestsByStatusAndTimePeriod("adminpending",0, true , searchField, searchValue));
		}
		
		return reqSummaryMap; 
	}
	
	/*
	 * Return list of all request based on user title 
	 * @param uid
	 *  
	 */
	public Map<String, List<Profile>> getRequestsByUserTitles(List<String> userTitles, String searchField, String searchValue){
		
		Map <String, List<Profile>> reqSummaryMap = new HashMap<String, List<Profile>>();
		if(!userTitles.isEmpty() && userTitles.contains("cw")){
			reqSummaryMap.put("cw", this.findRequestsByUserTitles("cw", searchField, searchValue));
		} 
		if (userTitles.contains("ss")){
			reqSummaryMap.put("ss", this.findRequestsByUserTitles("ss", searchField, searchValue));
		} 
		if (userTitles.contains("fa")){
			reqSummaryMap.put("fa", this.findRequestsByUserTitles("fa", searchField, searchValue));
		}

		return reqSummaryMap; 
	}

@SuppressWarnings("unchecked")
/*
 * Return list of all request with a given status and for number of days specified as period
 * @param status
 * @param period
 *  
 */
public List<CWRequest> findRequestsByStatusAndTimePeriod(String status, int period, boolean useRange, String serachField, String searchValue) {
	
	
	PersistenceManager pm = PMF.get().getPersistenceManager();
	

	String [] sts = null;
	if(!status.isEmpty() && status.equals("pending")){
		sts = new String [] {RequestStatusTypes.PENDINGFACTAPPROVAL.getLabel(), RequestStatusTypes.PENDINGSUPERVISORAPPROVAL.getLabel(), RequestStatusTypes.NEW.getLabel()};
	} else if (status.equals("approved")){
		sts = new String []{RequestStatusTypes.APPROVED.getLabel()};
	} else if (status.equals("denied")){
		sts = new String []{RequestStatusTypes.DENIED.getLabel(), RequestStatusTypes.SUPERVISORDENIED.getLabel()};
	} else if (status.equals("overdue")){
		sts = new String []{RequestStatusTypes.PENDINGRECEIPTS.getLabel(), RequestStatusTypes.RECEIPTAVAILABLE.getLabel()};
	} else if (status.equals("closed")){
		sts = new String []{RequestStatusTypes.CLOSED.getLabel()};	
	} else if (status.equals("adminpending")){
		sts = new String []{RequestStatusTypes.PENDINGFACTAPPROVAL.getLabel()};	
	} 
	//<TODO> add period conditions			
	StringBuffer qStr = new StringBuffer("SELECT from com.fact.wps.model.CWRequest where :r1.contains(status) ");
	Calendar dateParam  = null;
	dateParam = DateUtil.getDateFromPeriod(period);
		//qStr.append(" && date > DATE(").append(dateParam.get(Calendar.YEAR)).append(",").append(dateParam.get(Calendar.MONTH)+ 1).append(",").append(dateParam.get(Calendar.DATE)).append(")");
	qStr.append(" && date > ").append(dateParam.getTimeInMillis());
	
//	if(serachField != null && !serachField.isEmpty()) {
//		qStr.append(" && ").append(serachField).append( " == \'").append(searchValue).append("\'");
//	}
	
	//System.out.println("Query : " + qStr.toString());
	//System.out.println("Date : " + dateParam.getTime());

	Query q = pm.newQuery(qStr.toString());
	q.setOrdering("date asc");
	if (useRange) {
		q.setRange(0, 10);
	}
	//q.declareParameters("dateparam");
	List<CWRequest> results = null;

	try {
		results = (List<CWRequest>) q.execute(Arrays.asList(sts));
		
		if(results == null){
			results = new ArrayList<CWRequest>();
		} else {
			results =  (List<CWRequest>) pm.detachCopyAll(results);
		}

	} finally {
		q.closeAll();
		pm.close();
	}		
	
	// Filter by search field and value
    if (searchValue != null && !searchValue.equals("*")) {
        
        for (int i=results.size()-1; i>=0; i--) {
        	CWRequest cwr = results.get(i);
               
               switch (serachField) {
               case "childName":
                     if (!cwr.getChildName().toUpperCase().contains(searchValue.toUpperCase()))
                            results.remove(i);
                     break;
               case "requestorFirstName":
                     if (!cwr.getRequestorFirstName().toUpperCase().contains(searchValue.toUpperCase()))
                            results.remove(i);
                     break;
               case "pid":
                     if (!cwr.getPid().toUpperCase().contains(searchValue.toUpperCase()))
                            results.remove(i);
                     break;
               case "requestedVendor":
                     if (!cwr.getRequestedVendor().toUpperCase().contains(searchValue.toUpperCase()) )
                            results.remove(i);
                     break;
               default:
                     break;
               }
        }
               
 }


	return results;
}

@SuppressWarnings("unchecked")
/*
 * Return list of all request with a given status and for num of days specified as period
 * @param status
 * @param period
 *  
 */
public List<Profile> findRequestsByUserTitles(String title, String searchField, String searchValue) {
	
	
	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	StringBuffer qStr = new StringBuffer("SELECT from com.fact.wps.model.Profile where :r1.contains(title) && title == \"");
	qStr.append(title).append("\"");
	Query q = pm.newQuery(qStr.toString());
	List<Profile> results = null;

	try {
		results = (List<Profile>) q.execute(title);
		
		if (results == null) {
			results = new ArrayList<Profile>();
		} else {
			results =  (List<Profile>) pm.detachCopyAll(results);
		}

	} finally {
		q.closeAll();
		pm.close();
	}
	
	if (searchValue != null && !searchValue.equals("*")) {
		
		for (int i=results.size()-1; i>=0; i--) {
			Profile profile = results.get(i);	
			
			switch (searchField) {
			case "firstName":
				if (!profile.getFirstName().toUpperCase().contains(searchValue.toUpperCase()))
					results.remove(i);
				break;
			case "lastName":
				if (!profile.getLastName().toUpperCase().contains(searchValue.toUpperCase()))
					results.remove(i);
				break;
			case "unitNumber":
				if (profile.getUnitNumber() == null)
					results.remove(i);
				else if (!profile.getUnitNumber().toUpperCase().contains(searchValue.toUpperCase()))
					results.remove(i);
				break;
			case "supervisor":
				boolean bSupervisorFirstNameContains = false;
				boolean bSupervisorLastNameContains = false;
				if ((profile.getSupervisorFirstName() != null && profile.getSupervisorFirstName().toUpperCase().contains(searchValue.toUpperCase())) )
					bSupervisorFirstNameContains = true;
				if (profile.getSupervisorLastName() != null && profile.getSupervisorLastName().toUpperCase().contains(searchValue.toUpperCase()))
					bSupervisorLastNameContains = true;
				if (!bSupervisorFirstNameContains && !bSupervisorLastNameContains)
					results.remove(i);
				break;
			default:
				break;
			}
		}
			
	}

	return results;
}


/*
 * Return attachment details for request
 * @param reqkey
 *  
 */
public List<CPSRequestAttachment> getRequestAttachments(String reqkey){
	
	
	PersistenceManager pm = PMF.get().getPersistenceManager();

	Query q = pm.newQuery(CPSRequestAttachment.class);
	q.setFilter("cwRequestKey == reqkey");		
	q.declareParameters("String reqkey");

	List<CPSRequestAttachment> results = null;

	try {
		results = (List<CPSRequestAttachment>) q.execute(reqkey);
		
		if(results == null){
			results = new ArrayList<CPSRequestAttachment>();
		} else {
			results =  (List<CPSRequestAttachment>) pm.detachCopyAll(results);
		}

	} finally {
		q.closeAll();
		pm.close();
	}		

	return results;

}


/*
 * Remove attachment from the CPSRequestAttachment
 * @param attachmentKey
 */
public void removeAttachment(String fileKey){
	
	PersistenceManager pm = PMF.get().getPersistenceManager();


	Query q = pm.newQuery(CPSRequestAttachment.class);
	q.setFilter("fileKey == attachmentKey");		
	q.declareParameters("String attachmentKey");

	List<CPSRequestAttachment> results = null;

	try {
		results = (List<CPSRequestAttachment>) q.execute(fileKey);
		CPSRequestAttachment attachment = results.get(0);
		pm.deletePersistent(attachment);		
	} finally {
		q.closeAll();
		pm.close();
	}		
	
}
@SuppressWarnings("unchecked")
/*
 * Return list of all request with a given status and for the given period
 * @param status
 * @param start
 * @param finish
 *  
 */
public List<CWRequest> findRequestsByStatusAndTimePeriod(String status, Calendar startDate, Calendar endDate) {
	
	
	PersistenceManager pm = PMF.get().getPersistenceManager();		

	String [] sts = null;
	if(!status.isEmpty() && status.equals("pending")){
		sts = new String [] {RequestStatusTypes.PENDINGFACTAPPROVAL.getLabel(), RequestStatusTypes.PENDINGSUPERVISORAPPROVAL.getLabel(), RequestStatusTypes.NEW.getLabel()};
	} else if (status.equals("approved")){
		sts = new String []{RequestStatusTypes.APPROVED.getLabel()};
	} else if (status.equals("denied")){
		sts = new String []{RequestStatusTypes.DENIED.getLabel()};
	} else if (status.equals("overdue")){
		sts = new String []{RequestStatusTypes.PENDINGRECEIPTS.getLabel()};
	} else if (status.equals("closed")){
		sts = new String []{RequestStatusTypes.CLOSED.getLabel()};	
	} 
	//<TODO> add period conditions
	
	StringBuffer qStr = new StringBuffer("SELECT from com.fact.wps.model.CWRequest where :r1.contains(status) ");

		//qStr.append(" && date > DATE(").append(dateParam.get(Calendar.YEAR)).append(",").append(dateParam.get(Calendar.MONTH)+ 1).append(",").append(dateParam.get(Calendar.DATE)).append(")");
	qStr.append(" && date > " + startDate.getTimeInMillis());
//	qStr.append(" && date < ").append(endDate.getTimeInMillis());
	
	//System.out.println("Query : " + qStr.toString());
	//System.out.println("Date : " + dateParam.getTime());

	Query q = pm.newQuery(qStr.toString());
	q.setOrdering("date asc");
	
//	q.setFilter("date >= " + startDate.getTime());
//	q.setFilter("date <= " + endDate.getTimeInMillis());

	List<CWRequest> results = null;

	try {
		results = (List<CWRequest>) q.execute(Arrays.asList(sts));
		
		if(results == null){
			results = new ArrayList<CWRequest>();
		} else {
			results =  (List<CWRequest>) pm.detachCopyAll(results);
		}

	} finally {
		q.closeAll();
		pm.close();
	}		

	return results;
}

/**
 * This is divided into two main parts:
 * 	First part makes query to the DB and gets all the requests.
 *  Second part uses those requests and builds the reports. This can be optimized to build reports in one pass.
 *  Current implementation makes one pass for every type of report.
 * @return
 */
public Map <String, List<AdminReport>> fillAdminReports(HttpServletRequest request)
{
	Map <String, List<AdminReport>> reports = new HashMap<String, List<AdminReport>>();
	// use a query to read data and put it in appropriate reports
	String selected_year = request.getParameter("SELECTED_YEAR");
	int year = 0;

	Calendar cDate = Calendar.getInstance(); // current date
	// if year is not set the default is current year
	if (selected_year != null)
		year = Integer.parseInt(selected_year);
	else
		year = cDate.get(Calendar.YEAR);
	//System.out.println("Selected Year: "+ year);
	Calendar startDate = Calendar.getInstance();
	startDate.set(year, 11, 1, 0, 0, 0);     // starting date
	// get all the requests till today for the current year
	List <CWRequest> cwrequests = this.findRequestsByStatusAndTimePeriod("approved", startDate, cDate);
	List <CWRequest> closedRequests = this.findRequestsByStatusAndTimePeriod("closed", startDate, cDate);
		
	cwrequests.addAll(closedRequests);	
	
//	System.out.println("Start date = "+ startDate.toString());
//	System.out.println("Current date = "+ cDate.toString());
	//System.out.println("Approved and closed Req = "+ cwrequests.size());
	//System.out.println("Closed Req = "+ closedRequests.size());
	
	reports.put(ReportTypes.COUNTYANDAMOUNT.toString(), buildReportType(ReportTypes.COUNTYANDAMOUNT, cwrequests, year));
	reports.put(ReportTypes.CATEGORYANDAMOUNT.toString(), buildReportType(ReportTypes.CATEGORYANDAMOUNT, cwrequests, year));
	reports.put(ReportTypes.COUNTYANDNUMBEROFREQUESTS.toString(), buildReportType(ReportTypes.COUNTYANDNUMBEROFREQUESTS, cwrequests, year));
	reports.put(ReportTypes.CATEGORYANDNUMBEROFREQUESTS.toString(), buildReportType(ReportTypes.CATEGORYANDNUMBEROFREQUESTS, cwrequests, year));
	
	return reports;
}
/**
* This is the main function that builds reports. It loops through all the requests.
* For each request, it checks if report is already created for that type, if yes, then adds to it else
* creates a new instance.
* TODO: The year filter is added here because JDO query filter is not working.
*/
private List<AdminReport> buildReportType(ReportTypes reportType, List <CWRequest> cwrequests, int year)
{
	List<AdminReport> reportList = new ArrayList<AdminReport>();
	AdminReport report = null;
	List<Double> amountList = null;
	List <Integer> countList = null;

	for (CWRequest req : cwrequests)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(req.getDate());
		if (year != cal.get(Calendar.YEAR))
			continue; // skip this request
		int month = cal.get(Calendar.MONTH);
		// check if report already exists in reportList for this type
		if (reportType == ReportTypes.COUNTYANDAMOUNT || reportType == ReportTypes.COUNTYANDNUMBEROFREQUESTS)
			report = findReport(req.getCountyInRegion7(), "county", reportList);
		else
			report = findReport(req.getCategory(), "category", reportList);
		if (report == null)
		{
			report = new AdminReport();
			report.setReportType(reportType);
			if (reportType == ReportTypes.COUNTYANDAMOUNT || reportType == ReportTypes.COUNTYANDNUMBEROFREQUESTS)
				report.setCountyName(req.getCountyInRegion7());
			else
				report.setCategoryName(req.getCategory());
			reportList.add(report);
		}
		if (reportType == ReportTypes.COUNTYANDAMOUNT || reportType == ReportTypes.CATEGORYANDAMOUNT)
		{
			amountList = (List<Double>)report.getMonthlyAmount();
			amountList.set(month, amountList.get(month).doubleValue() + req.getReqestedAmount());
			report.setMonthlyAmount(amountList);
		}
		else
		{
			countList = (List<Integer>)report.getMonthlyCount();
			countList.set(month, countList.get(month).intValue() + req.getNumOfChildernSupported());	
			report.setMonthlyCount(countList);
		}			
	}
	return reportList;
}

/**
 *  private function to search for admin report of given type
 * @param param
 * @param paramType
 * @param reportList
 * @return
 */
private AdminReport findReport(String param, String paramType, List<AdminReport> reportList)
{
	AdminReport report = null;
	if (paramType == "county")
	{
		for (AdminReport rep : reportList)
		{
			if (rep.getCountyName() != null && rep.getCountyName().equalsIgnoreCase(param))
			{
				report = rep;
				break;
			}
		}
	}
	else if (paramType == "category")
	{
		for (AdminReport rep : reportList)
		{
			if (rep.getCategoryName() != null && rep.getCategoryName().equalsIgnoreCase(param))
			{
				report = rep;
				break;
			}
		}
	}
	return report;
}

private int getNextRequestNumber(){
		int nextSeqNum = 0;
	// First find exiting record in db,
	// if record found, get id and save updates
	// if not found, insert a new record.
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(SequenceNumber.class);
		q.setFilter("sequenceType == type");		
		q.declareParameters("String type");

		List<SequenceNumber> results = null;

		try {
			results = (List<SequenceNumber>) q.execute(SequenceNumber.SEQ_TYPE_REQ_NUM);
			if (!results.isEmpty()) {
				SequenceNumber seqNum = (SequenceNumber)results.get(0);
				nextSeqNum = seqNum.getNextRequestnumber();

			} else {
				//Insert new record
				SequenceNumber seqNum = new SequenceNumber();
				seqNum.setSequenceType(SequenceNumber.SEQ_TYPE_REQ_NUM);
				seqNum.setCurrentNumber(nextSeqNum);
				nextSeqNum = seqNum.getNextRequestnumber();
				pm.makePersistent(seqNum);
			}				
		} finally {
			q.closeAll();
			pm.close();
		}			
		return nextSeqNum;
	
}
}
