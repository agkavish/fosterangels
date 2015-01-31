/**
 * 
 */
package com.fact.wps.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author kagarwal
 *
 */
@PersistenceCapable (detachable="true")
public class SequenceNumber {
	public final static String SEQ_TYPE_REQ_NUM = "REQ_NUM";
	
	@Persistent
	/**
	 * Sequence Type will be RequestNumber, AccountNumber etc.
	 */
	private String sequenceType = SEQ_TYPE_REQ_NUM;
	
	/**
	 * @return the sequenceType
	 */
	public String getSequenceType() {
		return sequenceType;
	}

	/**
	 * @param sequenceType the sequenceType to set
	 */
	public void setSequenceType(String sequenceType) {
		this.sequenceType = sequenceType;
	}

	/**
	 * @return the currentNumber
	 */
	public int getCurrentNumber() {
		return currentNumber;
	}

	/**
	 * @param currentNumber the currentNumber to set
	 */
	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

	@Persistent
	private int currentNumber;


	
	public int getNextRequestnumber(){
		this.currentNumber += 1;
		return this.currentNumber;
	}

}
