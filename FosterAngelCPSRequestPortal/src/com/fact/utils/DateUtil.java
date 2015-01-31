/**
 * 
 */
package com.fact.utils;

import java.util.Calendar;

/**
 * @author kagarwal
 *
 */
public class DateUtil {
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATE_FORMAT_US = "MM/dd/yyyy";
	private static Calendar c = Calendar.getInstance();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Date 3 days fron now " + DateUtil.getDateFromPeriod(3));
		System.out.println("Date 4 days before " + DateUtil.getDateFromPeriod(-4));
	}
	
	/**
	 * returns date before/after period from current date. For prior date please pass negative numbers.
	 * @param period
	 * @return
	 */
	public static Calendar getDateFromPeriod(int period){		
		c.add(Calendar.DATE, period);
		return c;
		
	}

}
