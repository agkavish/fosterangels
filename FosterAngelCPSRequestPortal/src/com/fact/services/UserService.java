/**
 * 
 */
package com.fact.services;

import java.util.ArrayList;
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

import com.fact.FACTAppProperties;
import com.fact.PMF;
import com.fact.common.EmailTemplateTypes;
import com.fact.common.ProfileStatusTypes;
import com.fact.wps.model.AdminSummary;
import com.fact.wps.model.Profile;
import com.fact.wps.model.VerificationToken;
import com.google.appengine.api.datastore.Key;

/**
 * @author kagarwal
 *
 */
public class UserService {
	
	/**
	 * Validates User log in details with the system of records.
	 * 
	 * <TODO> Real implementation
	 * @param uId
	 * @param uPwd
	 * @return
	 */
	public Profile validateSignIn(String uId, String uPwd) {
		boolean isValidUser = false;
		Profile p = null;
		if (uId != null && !uId.isEmpty() && uPwd != null && !uPwd.isEmpty())
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Profile.class);
			q.setFilter("email == emailParameter");
			q.setOrdering("date desc");
			q.declareParameters("String emailParameter");
			
			try {
				@SuppressWarnings("unchecked")
				List<Profile> results = (List<Profile>) q.execute(uId.toLowerCase());

				if (results.isEmpty()) {
					isValidUser = false;
				} else {
					// Check for password
					p = results.get(0);
					if(p != null) {
					if(p.getProfileStatus() == ProfileStatusTypes.ACTIVE && p.getuPassword().equals(uPwd)){
							isValidUser = true;
						}
					}
					
				}
			} finally {
				q.closeAll();
				pm.close();
			}

			
		} else {
			isValidUser = false;
		}
		
		if(isValidUser)
		return p;
		else 
			return null;
	}

	public boolean validateSignInUsingKey(String key, String uPwd) {
		boolean isValidUser = false;
		Profile p = null;
		if (key != null && !key.isEmpty() && uPwd != null && !uPwd.isEmpty())
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Profile.class);
			q.setFilter("key == keyParameter");
			q.setOrdering("date desc");
			q.declareParameters("String keyParameter");
			
			try {
				@SuppressWarnings("unchecked")
				List<Profile> results = (List<Profile>) q.execute(key);

				if (results.isEmpty()) {
					isValidUser = false;
				} else {
					// Check for password
					p = results.get(0);
					if(p != null) {

					if(p.getProfileStatus() == ProfileStatusTypes.ACTIVE && p.getuPassword().equals(uPwd)){
							isValidUser = true;
						}
					}
					
				}
			} finally {
				q.closeAll();
				pm.close();
			}

			
		} else {
			isValidUser = false;
		}

		
		return isValidUser;		
	}
	
	/**
	 * Add a new user profile
	 * @param profile
	 */
	public void addUser(Profile profile) {
		if(profile != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(profile);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				pm.close();
			}
		}
	}
	
	/**
	 * Add a new user profile
	 * @param profile
	 */
	public Profile updateUser(Profile profile, Object key) {
		Profile p = null;
		if(profile != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {

				p = pm.getObjectById(Profile.class, key);				
				p.setCellPhone(profile.getCellPhone());
				//p.setEmail(profile.getEmail());
				p.setFirstName(profile.getFirstName());
				p.setLastName(profile.getLastName());
				p.setPreferedName(profile.getPreferedName());
				if(profile.getSupervisorFirstName() != null){
					p.setSupervisorFirstName(profile.getSupervisorFirstName());
				}
				
				if(profile.getSupervisorLastName()!= null){
					p.setSupervisorLastName(profile.getSupervisorLastName());
				}
				
				if(profile.getSupervisorEmail() != null) {
					p.setSupervisorEmail(profile.getSupervisorEmail());
				}
				
				if(profile.getSupervisorPhone() != null) {
					p.setSupervisorPhone(profile.getSupervisorPhone());
				}
				
				if(profile.getTitle() != null && !profile.getTitle().isEmpty()) {
					p.setTitle(profile.getTitle());
				}
				p.setWorkPhone(profile.getWorkPhone());
				
				if(profile.getUnitNumber() != null) {
					p.setUnitNumber(profile.getUnitNumber());
				}
				
				if(profile.getuPassword() != null && !profile.getuPassword().isEmpty()) {
					p.setuPassword(profile.getuPassword() );
				}
				p.setLastUpdatedDate(new Date());
				//p.setFirstTimeFactUser(profile.getFirstTimeFactUser());
			} finally {

				pm.close();
			}

		}
		return p;
	}
	
	/**
	 * Updates user status
	 * @param emailId
	 * @param profileStatus
	 */
	public void updateUserStatus(String emailId, ProfileStatusTypes profileStatus) {
		Profile p = null;
		if(emailId != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				Query q = pm.newQuery(Profile.class);
				q.setFilter("email == emailParameter");
				q.declareParameters("String emailParameter");
				q.setOrdering("date desc");
				@SuppressWarnings("unchecked")
				List<Profile> results = (List<Profile>) q.execute(emailId);
				if (!results.isEmpty()) {
					 p = results.get(0);
					p.setProfileStatus(profileStatus);
				} 

			} finally {
				pm.close();
			}

		}
	}

	// TODO: should this be com.google.appengine.api.datastore.Key? Added another function to do the same
	public Profile getProfileByKey(String key) {
		
		Profile p = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			p = pm.getObjectById(Profile.class, key);

			
		} finally {
			pm.close();
		}
		return p;
	}
	// TODO: overloaded the function with datastore key
public Profile getProfileByKey(Key key) {
		
		Profile p = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			p = pm.getObjectById(Profile.class, key);

			
		} finally {
			pm.close();
		}
		return p;
	}

	/**
	 * Check if user id is available 
	 * @param profile
	 * @return boolean
	 */
	public boolean isUserIdVailable(Profile profile) {
		boolean isUserIDAvailable = false;
		if(profile != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Profile.class);
			q.setFilter("email == emailParameter");
			q.setOrdering("date desc");
			q.declareParameters("String emailParameter");

			try {
				@SuppressWarnings("unchecked")
				List<Profile> results = (List<Profile>) q.execute(profile.getEmail());

				if (results.isEmpty()) {
					isUserIDAvailable = true;
				} else {
					isUserIDAvailable = false;
				}
			} finally {
				q.closeAll();
				pm.close();
			}

		}
		return isUserIDAvailable;
	}
	
	public boolean sendVerificationToken(Profile profile, VerificationToken token) {
		// TODO implement method!
		// Use MailSenderService to Send the mail. (Make sure that the mail contains a new link w/Profile info)
		// Add the token to DB
		// Return True if process is successful.
		

		String toEmail = profile.getEmail();
		String fromEmail = FACTAppProperties.FACT_ADMIN_EMAIL;
		String subject = "FOSTER ANGELS: Email Verification Needed";
		MailSenderService mailServ = new MailSenderService();
		MimeMessage msg = mailServ.createMessage(); 
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false)); //profile.getEmail();

//			if (ccEmail.length() > 0) {
//				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
//			}

			msg.setSubject(subject);
			Map<String, String> msgData = new HashMap<String, String>();
			msgData.put("fname", profile.getFirstName());
			msgData.put("verificationcode", token.getToken());
			
			String msgTxt = mailServ.getEmailMessage(EmailTemplateTypes.USERVERIFICATION, msgData);
			msg.setText(msgTxt, "utf-8");
			msg.setSentDate(new Date());

			mailServ.sendEmail(msg);
			return true;

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Email sent!");
		}
		return false;
		
	}
	/**
	 * Fills the admin related summary of all users. Common for all Admins
	 */
	public void fillAdminUserSummary(AdminSummary adminSummary)
	{
		// get All new profiles
		List<Profile> results = this.getAllNewUsers();
		int newCaseWorkersCount = 0;
		int newSupportStaffCount = 0;

		for (Profile req : results){			
			if(req.getTitle().equalsIgnoreCase("cw")){ // TODO: use ENUM
				newCaseWorkersCount += 1;
			}
			if(req.getTitle().equalsIgnoreCase("ss")){ // TODO: use ENUM
				newSupportStaffCount += 1;
			}
		}
		
// set on MODEL and return
		if (adminSummary == null)
			adminSummary = new AdminSummary();		
		adminSummary.setNewCaseWorkers(newCaseWorkersCount);
		adminSummary.setNewSupportStaff(newSupportStaffCount);
	}
	/**
	 * Returns all NEW users for Admin display
	 * TODO: Assume there won't be many new users
	 */
	@SuppressWarnings("unchecked")
	private List<Profile> getAllNewUsers()
	{
		String filterStatus = "NEW";
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Profile.class);
		q.setFilter("profileStatus == filter");		
		q.declareParameters("String filter");
		q.setOrdering("date desc");

		List<Profile> results = null;

		try 
		{
			results = (List<Profile>) q.execute(filterStatus);
			
			if(results == null)
			{
				results = new ArrayList<Profile>();
			} else 
			{
				results =  (List<Profile>) pm.detachCopyAll(results);
			}

		} finally 
		{
			q.closeAll();
			pm.close();
		}		

		return results;
	}
	
	/**
	 * Fech user detail suing email as key.
	 * 
	 * 
	 * @param email
	 * @return
	 */
	public Profile getProfileByEmail(String email) {
		Profile p = null;
		if (email != null && !email.isEmpty())
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Profile.class);
			q.setFilter("email == emailParameter");
			q.setOrdering("date desc");
			q.declareParameters("String emailParameter");
			
			try {
				@SuppressWarnings("unchecked")
				List<Profile> results = (List<Profile>) q.execute(email);

					p = results.get(0);
					
			} finally {
				q.closeAll();
				pm.close();
			}

			
		} return p;
	}
}
