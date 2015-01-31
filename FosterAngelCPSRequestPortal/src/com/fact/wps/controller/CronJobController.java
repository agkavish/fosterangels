package com.fact.wps.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fact.FACTAppProperties;
import com.fact.PMF;
import com.fact.wps.model.CWRequest;

@Controller
@RequestMapping("/cron")

public class CronJobController {
	private static final String GET_STATUS="Approved";
	private static final String SET_STATUS = "PendingReceipts";
	
	@RequestMapping(value="/changeStatus", method = RequestMethod.GET)
	public ModelAndView updateStatus(HttpServletRequest request,ModelMap map) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -FACTAppProperties.NUM_DAYS_OVERDUE_REQUEST);
		Date date = cal.getTime();
        List<Integer> ls = new ArrayList<Integer>();
		try {
           Query q = pm.newQuery(CWRequest.class);
           q.setFilter("status == :statusParam");
           pm.currentTransaction().begin();
           List<CWRequest> results = (List<CWRequest>)q.execute(GET_STATUS);

           if(!results.isEmpty())
           {
        	   for(CWRequest cw: results)
        	   {
        		   if(cw.getLastUpdatedDate() != null)
        		   {
	        		   if((cw.getLastUpdatedDate()).before(date))
	        		   {
	        		      cw.setStatus(SET_STATUS);
	        		      cw.setLastUpdatedDate(new Date());
	        		      cw.setLastUpdatedUser("SYSTEM");
	        		      ls.add(cw.getRequestNumber());
	        		   }
        		   }
        	   }
           }
           pm.currentTransaction().commit();
	
		} catch(Throwable t)
		{
			pm.currentTransaction().rollback();
			t.printStackTrace();
		}
		
		finally {
			pm.close();
		}
		request.getSession().setAttribute("request_ids", ls);
		return new ModelAndView("changeStatus");
		
	}

}
