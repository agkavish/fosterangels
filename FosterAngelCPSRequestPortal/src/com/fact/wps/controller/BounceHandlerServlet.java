package com.fact.wps.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fact.PMF;
import com.fact.common.ProfileStatusTypes;
import com.fact.wps.model.Profile;
import com.google.appengine.api.mail.BounceNotification;
import com.google.appengine.api.mail.BounceNotificationParser;

public class BounceHandlerServlet extends HttpServlet{
	private static final Date d = new Date();
	
	@Override
    public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException {

		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
		    BounceNotification bounce = BounceNotificationParser.parse(req);
		    String email = bounce.getOriginal().getTo();
			Query q = pm.newQuery(Profile.class);
	        pm.currentTransaction().begin();
	        q.setFilter("email == :emailParam");
	        List<Profile> results = (List<Profile>)q.execute(email);
	        for(Profile p: results)
	        {
	        	p.setProfileStatus(ProfileStatusTypes.DISABLED);
	        	p.setLastUpdatedDate(d);
	        }
            pm.currentTransaction().commit();
		} catch(Exception e)
		{
			 e.printStackTrace();
		     pm.currentTransaction().rollback();
		}
		finally {
            pm.close();
		}
		
	}

		
}
