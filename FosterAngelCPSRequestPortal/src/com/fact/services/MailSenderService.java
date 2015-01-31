package com.fact.services;

import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.fact.common.EmailTemplateTypes;

//import com.sun.mail.smtp.SMTPTransport;

/**
 * @author kagarwal
 * 
 */
public class MailSenderService {
	public static Properties props = null;
	public static Session session = null;

	static {
		props = new Properties();
		session = Session.getDefaultInstance(props, null);
	}

	public MimeMessage createMessage() {
		MimeMessage aMsg = new MimeMessage(MailSenderService.session);
		return aMsg;
	}

	public void sendEmail(Message aMessage) {
		try {
			Transport.send(aMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendEmail() {

	//	System.out.println("Sending email.");
		

		String toEmail = "mehmeteyesin@gmail.com";
		String fromEmail = "mehmeteyesin@gmail.com";
		String ccEmail = "mehmeteyesin@yahoo.com";

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		Properties props = System.getProperties();
		props.setProperty("mail.smtps.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.setProperty("mail.smtps.auth", "true");
		props.put("mail.smtps.quitwait", "false");

		Session session = Session.getInstance(props, null);
		final MimeMessage msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			if (ccEmail.length() > 0) {
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
			}

			msg.setSubject("Subject");
			msg.setText("Message Body", "utf-8");
			msg.setSentDate(new Date());

	//		SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
			//t.connect("smtp.gmail.com", "chimegwt", "?????!!!!!");
			//t.sendMessage(msg, msg.getAllRecipients());
			//t.close();

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Email sent!");
		}

	}
	
	/**
	 * This function read velocity template file based on type and apply values available in the
	 * passed map to the template to generate email message string.
	 * Assuming all the templates are in templates folder. 
	 * @param type
	 * @param msgData
	 * @return
	 */
	public String getEmailMessage(EmailTemplateTypes type, Map<String, String> msgData){
	
		    if (type != null ){
		        /*  first, get and initialize an engine  */
		        VelocityEngine ve = new VelocityEngine();
		        ve.init();
		        /*  next, get the Template  */
		        String templateFileName = "";
		        if (type.equals(EmailTemplateTypes.USERVERIFICATION)){
		        	templateFileName = "profileverification.vm";
		        } else  if (type.equals(EmailTemplateTypes.REQSUPERVISORVERIFICATION)){
		        	templateFileName = "supervisorconfirmation.vm";
		        } else  if (type.equals(EmailTemplateTypes.REQFACTNOIFCATION)){
		        	templateFileName = "requestnotificationfact.vm";

		        } else  if (type.equals(EmailTemplateTypes.CHECKACTIVESTATUS)){
		        	templateFileName = "activeStatus.vm";
		        }
		         else if (type.equals(EmailTemplateTypes.REQSTATUSUPDATE)){
		        	templateFileName = "approvedreqar.vm";
		        }
		       Template t = ve.getTemplate( "templates/" + templateFileName );
		        /*  create a context and add data */
		        VelocityContext context = new VelocityContext();
		        
		        if (msgData != null){
		        	Set keys = msgData.keySet();
		        	Iterator it = keys.iterator();
		        	while(it.hasNext()){
		        		String k1 = (String) it.next();
		        		String v1 = msgData.get(k1);
		        		context.put(k1, v1);
		        	}
		        }
		        /* now render the template into a StringWriter */
		        StringWriter writer = new StringWriter();
		        t.merge( context, writer );
		        /* show the World */
		        Logger.getGlobal().log(Level.INFO, writer.toString());
		       // System.out.println("EMAIL MESSAGE***" +  writer.toString() );   
		        return writer.toString();
		    } else {
		    	return "";
		    }
		    
	}

}
