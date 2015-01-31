/**
 * 
 */
package com.fact.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kagarwal
 *
 */
public enum RequestStatusTypes {
	
	NEW (-1, "New", "The new request created"),
	PENDINGSUPERVISORAPPROVAL (0, "PendingSupervisorApproval", "The request is pending for supervisor verification"),
	PENDINGFACTAPPROVAL(1, "PendingFACTApproval", "The reuqest is pending with FACT"),
	APPROVED(2, "Approved", "The request has been approved by FACT"),
	DELIVERED(3, "Delivered" , "Requested items has been delivered"),
	PENDINGRECEIPTS(4, "PendingReceipts", "The request pending for receipts upload"),
	CLOSED(5, "Closed", "The request has been closed"),
	DENIED(6, "Denied", "The request has been denied by FACT"),
	RECEIPTAVAILABLE(7, "ReceiptAvailable", "Receipts has been uploaded for the request"),
	SUPERVISORDENIED(8, "SupervisorDenied", "Request has been denied by supervisor");
	
	private int code;
    private String label;
    private String description;
	
	 /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, RequestStatusTypes> codeToStatusMapping;
 
    private RequestStatusTypes(int code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }
 
    public static RequestStatusTypes getStatus(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }
 
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, RequestStatusTypes>();
        for (RequestStatusTypes s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }
 
    public int getCode() {
        return code;
    }
 
    public String getLabel() {
        return label;
    }
 
    public String getDescription() {
        return description;
    }
 
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Status");
        sb.append("{code=").append(code);
        sb.append(", label='").append(label).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
