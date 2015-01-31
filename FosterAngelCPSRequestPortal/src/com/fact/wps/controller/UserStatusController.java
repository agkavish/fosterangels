package com.fact.wps.controller;

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
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fact.FACTAppProperties;
import com.fact.PMF;
import com.fact.common.EmailTemplateTypes;
import com.fact.common.ProfileStatusTypes;
import com.fact.services.MailSenderService;
import com.fact.wps.model.Profile;

@Controller
@RequestMapping("/cron")
public class UserStatusController {

	@RequestMapping(value="/checkUserStatus", method = RequestMethod.GET)
	public ModelAndView checkUserStatus(HttpServletRequest request,ModelMap map) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query q = pm.newQuery(Profile.class);
	        q.setFilter("profileStatus == :profileStatusParam");
	        @SuppressWarnings("unchecked")
			List<Profile> results = (List<Profile>)q.execute(ProfileStatusTypes.ACTIVE);
	        results = (List<Profile>) pm.detachCopyAll(results);
            sendEmails(results,request);
		}catch(Throwable t) {
			
		}
		finally {
			pm.close();
		}	
		return new ModelAndView("userStatus");
		
	}
	
private void sendEmails(List<Profile> results,HttpServletRequest request)
{
	List<String> emailList = new ArrayList<String>();
	for(Profile p: results)
	{
		String toEmail = p.getEmail();
		emailList.add(toEmail);
		String fromEmail = FACTAppProperties.FACT_ADMIN_EMAIL;
		String subject = "FOSTER ANGELS: Email Verification Needed";
		MailSenderService mailServ = new MailSenderService();
		MimeMessage msg = mailServ.createMessage();
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false)); 
			msg.setSubject(subject);
			Map<String, String> msgData = new HashMap<String, String>();
			msgData.put("fname", p.getFirstName());
			String msgTxt = mailServ.getEmailMessage(EmailTemplateTypes.CHECKACTIVESTATUS, msgData);
			msg.setText(msgTxt, "utf-8");
			msg.setSentDate(new Date());
			mailServ.sendEmail(msg);
		} catch (AddressException e) {
		   e.printStackTrace();
	    } catch (MessagingException e) {
		   e.printStackTrace();
	    } finally {

	    }
	}
	request.getSession().setAttribute("emails_sent", emailList);
}

}
