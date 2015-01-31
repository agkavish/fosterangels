package com.fact.wps.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.fact.common.ProfileStatusTypes;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable (detachable="true")
public class Profile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1422786665404340661L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String firstName = "" ;
	
	@Persistent
	private String lastName = "";
	
	@Persistent
	private String preferedName = "";
	
	@Persistent
	private String email = "";
	
	@Persistent
	private String supervisorFirstName = "";
	
	@Persistent
	private String supervisorLastName = "";
	
	@Persistent
	private String unitNumber = "";
	
	@Persistent
	private String title = "";
	
	@Persistent
	private String workPhone = "";
	
	@Persistent
	private String cellPhone = "";
	
	@Persistent
	private String supervisorEmail = "" ;
	
	@Persistent
	private String supervisorPhone = "";
	
	@Persistent
	private Boolean firstTimeFactUser = false;
	
	@Persistent
	private ProfileStatusTypes profileStatus ;
	
	@Persistent
	private String uPassword = "";
	
	@Persistent
	private Date lastUpdatedDate ;
	
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

	public ProfileStatusTypes getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(ProfileStatusTypes profileStatus) {
		this.profileStatus = profileStatus;
	}


	
	public String getSupervisorFirstName() {
		return supervisorFirstName;
	}

	public void setSupervisorFirstName(String supervisorFirstName) {
		this.supervisorFirstName = supervisorFirstName;
	}

	public String getSupervisorLastName() {
		return supervisorLastName;
	}

	public void setSupervisorLastName(String supervisorLastName) {
		this.supervisorLastName = supervisorLastName;
	}

	public String getuPassword() {
		return uPassword;
	}

	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public Boolean getFirstTimeFactUser() {
		return firstTimeFactUser;
	}

	
	
	
	
	//@Persistent
//	private Object picture ;	
	
	public Boolean isFirstTimeFactUser() {
		return firstTimeFactUser;
	}

	public void setFirstTimeFactUser(Boolean firstTimeFactUser) {
		if(firstTimeFactUser != null) {
		this.firstTimeFactUser = firstTimeFactUser;
		} else {
			this.firstTimeFactUser = true;
		}
	}

	@Persistent
    private Date date;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPreferedName() {
		return preferedName;
	}

	public void setPreferedName(String preferedName) {
		this.preferedName = preferedName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}


	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getSupervisorEmail() {
		return supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

	public String getSupervisorPhone() {
		return supervisorPhone;
	}

	public void setSupervisorPhone(String supervisorPhone) {
		this.supervisorPhone = supervisorPhone;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Profile [key=").append(key).append(", firstName=")
				.append(firstName).append(", lastName=").append(lastName)
				.append(", preferedName=").append(preferedName)
				.append(", email=").append(email)
				.append(", supervisorFirstName=").append(supervisorFirstName)
				.append(", supervisorLastName=").append(supervisorLastName)
				.append(", unitNumber=").append(unitNumber).append(", title=")
				.append(title).append(", workPhone=").append(workPhone)
				.append(", cellPhone=").append(cellPhone)
				.append(", supervisorEmail=").append(supervisorEmail)
				.append(", supervisorPhone=").append(supervisorPhone)
				.append(", firstTimeFactUser=").append(firstTimeFactUser)
				.append(", profileStatus=").append(profileStatus)
				.append(", uPassword=").append(uPassword)
				.append(", lastUpdatedDate=").append(lastUpdatedDate)
				.append(", date=").append(date).append("]");
		return builder.toString();
	}
	
	
	
	
}