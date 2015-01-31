/**
 * 
 */
package com.fact;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * This class loads properties from fact.properties file and expose some
 * regularly used properties
 * 
 * @author kagarwal
 * 
 */
public class FACTAppProperties {
	private static final String CONFIG_FILE = "/FACT.properties";
	private static Properties prop = new Properties();
	//public static final String FACT_ADMIN_EMAIL = "liz@fosterangelsctx.org";
	public static final String FACT_ADMIN_EMAIL = "fosterangelshelps@gmail.com";
	public static final int NUM_DAYS_OVERDUE_REQUEST = 30;	
	public static final String FACT_REQUEST_EMAIL_CC = "fosterangelshelps@gmail.com";


//	static {
//		try {
//			// load a properties file
//
//			// get the property value and print it out
//			System.out.println(prop);
//
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//	}

	public static void main(String [] args){
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE);
		//prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE));

		System.out.println(prop);
	}
}
