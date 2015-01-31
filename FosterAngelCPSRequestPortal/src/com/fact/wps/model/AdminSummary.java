/**
 * 
 */
package com.fact.wps.model;

/**
 * @author sshrotriya
 *
 */
public class AdminSummary {
	
	private int newRequests;
	private int newCaseWorkers;
	private int newSupportStaff;
	private int overdueRequests;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdminSummary [newRequests=")
				.append(newRequests).append(", newCaseWorkers=")
				.append(newCaseWorkers).append(", newSupportStaff=")
				.append(newSupportStaff).append(", overdueRequests=")
				.append(overdueRequests).append("]");
		return builder.toString();
	}

	public int getNewRequests() {
		return newRequests;
	}

	public void setNewRequests(int newRequests) {
		this.newRequests = newRequests;
	}

	public int getNewCaseWorkers() {
		return newCaseWorkers;
	}

	public void setNewCaseWorkers(int newCaseWorkers) {
		this.newCaseWorkers = newCaseWorkers;
	}

	public int getNewSupportStaff() {
		return newSupportStaff;
	}

	public void setNewSupportStaff(int newSupportStaff) {
		this.newSupportStaff = newSupportStaff;
	}

	public int getOverdueRequests() {
		return overdueRequests;
	}

	public void setOverdueRequests(int overdueRequests) {
		this.overdueRequests = overdueRequests;
	}

}
