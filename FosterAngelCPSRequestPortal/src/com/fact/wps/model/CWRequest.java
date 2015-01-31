/**
 * 
 */
package com.fact.wps.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.fact.common.RequestTypes;
import com.fact.utils.DateUtil;
import com.google.appengine.api.datastore.Key;

/**
 * @author kagarwal
 *
 */

@PersistenceCapable (detachable="true")
//@SequenceGenerator(initialValue = 0001, name = "SEQ_REQ_NUM_1", sequenceName = "FACT_SEQ_1")
public class CWRequest implements Serializable {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent//(valueStrategy = IdGeneratorStrategy.SEQUENCE)	
	private int requestNumber ;
	
	/**
	 * @return the requestNumber
	 */
	public int getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	@Persistent
	private String childName = "";
	
	@Persistent
	private String age = "";
	
	@Persistent
	private String gender = "";
	
	@Persistent
	private String pid = "";
	
	@Persistent
	private Date requestedDate ;
	
	@Persistent
	private double reqestedAmount ;
	
	@Persistent
	private int numberOfTimesUsedFact;
	
	@Persistent
	private String requestedVendor = "";	
	
	@Persistent	
	private String countyInRegion7 = "";
	
	@Persistent
	private String zipCode = "";
	
	@Persistent
	private String requestDescription = "";
	
	@Persistent
	private boolean sampleReqFlag = false;
	
	@Persistent
    private String status;
	
	@Persistent
    private String category;
	
	@Persistent
    private String requestorID;
	
	@Persistent
    private String creatorID;
	
	@Persistent
	private Date lastUpdatedDate ;
	
	@Persistent
	private String adminNotes = "";
	
	@Persistent
	private String requestorFirstName = "";
	
	@Persistent
	private String requestType = RequestTypes.CPS.getLabel();
	
	@Persistent
	private int numOfChildernSupported = 1;
	
	
	@Persistent
	private double purchasedAmount ;
	

	

	/**
	 * @return the purchasedAmount
	 */
	public double getPurchasedAmount() {
		return purchasedAmount;
	}

	/**
	 * @param purchasedAmount the purchasedAmount to set
	 */
	public void setPurchasedAmount(double purchasedAmount) {
		this.purchasedAmount = purchasedAmount;
	}

	@Persistent
	private String adminEmailNotes = "";
	/**
	 * @return the adminEmailNotes
	 */
	public String getAdminEmailNotes() {
		return adminEmailNotes;
	}

	/**
	 * @param adminEmailNotes the adminEmailNotes to set
	 */
	public void setAdminEmailNotes(String adminEmailNotes) {
		this.adminEmailNotes = adminEmailNotes;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the numOfChildernSupported
	 */
	public int getNumOfChildernSupported() {
		return numOfChildernSupported;
	}

	/**
	 * @param numOfChildernSupported the numOfChildernSupported to set
	 */
	public void setNumOfChildernSupported(int numOfChildernSupported) {
		this.numOfChildernSupported = numOfChildernSupported;
	}

	/**
	 * @return the requestorFirstName
	 */
	public String getRequestorFirstName() {
		return requestorFirstName;
	}

	/**
	 * @param requestorFirstName the requestorFirstName to set
	 */
	public void setRequestorFirstName(String requestorFirstName) {
		this.requestorFirstName = requestorFirstName;
	}
	
	/**
	 * @return the requestorFirstName
	 */
	public String getRequestorName() {

		StringBuffer name = new StringBuffer();
		if(requestorLastName == null || requestorLastName.equals("")){
			name.append("<LastName>");
		} else {
			name.append(requestorLastName);
		}
		
		name.append(", ");
		if(requestorFirstName == null || requestorFirstName.equals("")){
			name.append("<FirstName>");
		} else {
			name.append(requestorFirstName);
		}
		
		return name.toString();
	}

	/**
	 * @return the requestorLastName
	 */
	public String getRequestorLastName() {
		return requestorLastName;
	}

	/**
	 * @param requestorLastName the requestorLastName to set
	 */
	public void setRequestorLastName(String requestorLastName) {
		this.requestorLastName = requestorLastName;
	}

	@Persistent
	private String requestorLastName = "";
	
	
	
	/**
	 * @return the adminNotes
	 */
	public String getAdminNotes() {
		return adminNotes;
	}

	/**
	 * @param adminNotes the adminNotes to set
	 */
	public void setAdminNotes(String adminNotes) {
		this.adminNotes = adminNotes;
	}

	/**
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	@Persistent
	private String lastUpdatedUser ;
	
	
	/**
	 * @return the creatorID
	 */
	public String getCreatorID() {
		return creatorID;
	}

	/**
	 * @param creatorID the creatorID to set
	 */
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}

	public String getRequestorID() {
		return requestorID;
	}

	public void setRequestorID(String requestorID) {
		this.requestorID = requestorID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Persistent
    private Date date;
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}
	
	public String getRequestedDateString() {
		String dateStr = "";
		if(requestedDate != null){
			dateStr = new SimpleDateFormat(DateUtil.DATE_FORMAT_US).format(requestedDate); 
		}
		return dateStr;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public double getReqestedAmount() {
		return reqestedAmount;
	}

	public void setReqestedAmount(double reqestedAmount) {
		this.reqestedAmount = reqestedAmount;
	}

	public int getNumberOfTimesUsedFact() {
		return numberOfTimesUsedFact;
	}

	public void setNumberOfTimesUsedFact(int numberOfTimesUsedFact) {
		this.numberOfTimesUsedFact = numberOfTimesUsedFact;
	}

	public String getRequestedVendor() {
		return requestedVendor;
	}

	public void setRequestedVendor(String requestedVendor) {
		this.requestedVendor = requestedVendor;
	}

	public String getCountyInRegion7() {
		return countyInRegion7;
	}

	public void setCountyInRegion7(String countyInRegion7) {
		this.countyInRegion7 = countyInRegion7;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getRequestDescription() {
		return requestDescription;
	}

	public void setRequestDescription(String requestDescription) {
		this.requestDescription = requestDescription;
	}

	public boolean isSampleReqFlag() {
		return sampleReqFlag;
	}

	public void setSampleReqFlag(boolean sampleReqFlag) {
		this.sampleReqFlag = sampleReqFlag;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CWRequest [key=").append(key)
				.append(", requestNumber=").append(requestNumber)
				.append(", childName=").append(childName).append(", age=")
				.append(age).append(", gender=").append(gender)
				.append(", pid=").append(pid).append(", requestedDate=")
				.append(requestedDate).append(", reqestedAmount=")
				.append(reqestedAmount).append(", numberOfTimesUsedFact=")
				.append(numberOfTimesUsedFact).append(", requestedVendor=")
				.append(requestedVendor).append(", countyInRegion7=")
				.append(countyInRegion7).append(", zipCode=").append(zipCode)
				.append(", requestDescription=").append(requestDescription)
				.append(", sampleReqFlag=").append(sampleReqFlag)
				.append(", status=").append(status).append(", category=")
				.append(category).append(", requestorID=").append(requestorID)
				.append(", creatorID=").append(creatorID)
				.append(", lastUpdatedDate=").append(lastUpdatedDate)
				.append(", adminNotes=").append(adminNotes)
				.append(", requestorFirstName=").append(requestorFirstName)
				.append(", requestType=").append(requestType)
				.append(", numOfChildernSupported=")
				.append(numOfChildernSupported).append(", requestorLastName=")
				.append(requestorLastName).append(", lastUpdatedUser=")
				.append(lastUpdatedUser).append(", date=").append(date)
				.append("]");
		return builder.toString();
	}	
	
	

}
