/**
 * 
 */
package com.fact.wps.model;

/**
 * @author kagarwal
 *
 */
public class RequestSummary {
	
	private int approvedRequest;
	private int pendingRequest;
	private int closedRequest;
	private int requestInError;
	private int overdueRequest;
	private int deliveredRequest;
	private int receiptAvailableRequest;
	/**
	 * @return the receiptAvailableRequest
	 */
	public int getReceiptAvailableRequest() {
		return receiptAvailableRequest;
	}
	/**
	 * @param receiptAvailableRequest the receiptAvailableRequest to set
	 */
	public void setReceiptAvailableRequest(int receiptAvailableRequest) {
		this.receiptAvailableRequest = receiptAvailableRequest;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestSummary [approvedRequest=")
				.append(approvedRequest).append(", Delivered=")
				.append(deliveredRequest).append(", pendingRequest=")
				.append(pendingRequest).append(", closedRequest=")
				.append(closedRequest).append(", requestInError=")
				.append(requestInError).append(", overdueRequest=")
				.append(overdueRequest).append(", receiptAvailableRequest=")
				.append(receiptAvailableRequest).append("]");
		return builder.toString();
	}
	/**
	 * @return the approvedRequest
	 */
	public int getApprovedRequest() {
		return approvedRequest;
	}
	/**
	 * @param approvedRequest the approvedRequest to set
	 */
	public void setApprovedRequest(int approvedRequest) {
		this.approvedRequest = approvedRequest;
	}
	/**
	 * @return the pendingRequest
	 */
	public int getPendingRequest() {
		return pendingRequest;
	}
	/**
	 * @param pendingRequest the pendingRequest to set
	 */
	public void setPendingRequest(int pendingRequest) {
		this.pendingRequest = pendingRequest;
	}
	/**
	 * @return the closedRequest
	 */
	public int getClosedRequest() {
		return closedRequest;
	}
	/**
	 * @param closedRequest the closedRequest to set
	 */
	public void setClosedRequest(int closedRequest) {
		this.closedRequest = closedRequest;
	}
	/**
	 * @return the requestInError
	 */
	public int getRequestInError() {
		return requestInError;
	}
	/**
	 * @param requestInError the requestInError to set
	 */
	public void setRequestInError(int requestInError) {
		this.requestInError = requestInError;
	}
	/**
	 * @return the overdueRequest
	 */
	public int getOverdueRequest() {
		return overdueRequest;
	}
	/**
	 * @param overdueRequest the overdueRequest to set
	 */
	public void setOverdueRequest(int overdueRequest) {
		this.overdueRequest = overdueRequest;
	}
	public int getDeliveredRequest() {
		return deliveredRequest;
	}
	public void setDeliveredRequest(int delivered) {
		this.deliveredRequest = delivered;
	}

}
