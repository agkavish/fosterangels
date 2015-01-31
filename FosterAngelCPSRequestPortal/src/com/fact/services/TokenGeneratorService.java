package com.fact.services;

import java.util.List;
import java.util.Random;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.fact.PMF;
import com.fact.wps.model.Profile;
import com.fact.wps.model.VerificationToken;

public class TokenGeneratorService {

	private static Random ran = new Random();
	

	public static String generateToken() {
		String tokenCode = String.valueOf(ran.nextInt(9999));
		return tokenCode;
	}
	
	/**
	 * Add a new Token
	 * @param token
	 */
	public void addToken(VerificationToken token) {
		if(token != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			try {
				pm.makePersistent(token);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				pm.close();
			}
		}
	}
	
	
	/**
	 * This function validates the token provided by user.
	 * @param emailId
	 * @param token
	 * @return
	 */
	public boolean validateTokenByUserID(String emailId, String token){
		boolean isValid = false;
		if(token != null && !token.isEmpty() && emailId != null && !emailId.isEmpty()) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(VerificationToken.class);
			q.setFilter("userEmail == emailParameter");
			q.declareParameters("String emailParameter");
			q.setOrdering("timeStamp desc");

			try {
				@SuppressWarnings("unchecked")
				List<VerificationToken> results = (List<VerificationToken>) q.execute(emailId);

				if (!results.isEmpty()) {
					VerificationToken dToken = results.get(0);
					if(dToken.isActive() && dToken.getToken().equalsIgnoreCase(token)){
						isValid = true;
						dToken.setActive(false);
					}
				} 
			} finally {
				q.closeAll();
				pm.close();
			}

		}
		return isValid;
	}

}
