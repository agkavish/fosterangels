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
public enum EmailTemplateTypes {
	
	USERVERIFICATION (1, "User Verification", "For new user profiles"),
	REQSUPERVISORVERIFICATION (2, "Superviosr Approval", "Message to supervisor for new request approval"),
	REQFACTNOIFCATION(4, "FACT Notifcation", "New request notification to FACT admin"),
	REQSTATUSUPDATE(3, "Request Status", "Messasge to update request status"),
	CHECKACTIVESTATUS(5,"Active Status Check","Message to check if status is active");
	
	private int code;
    private String label;
    private String description;
	
	 /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, EmailTemplateTypes> codeToStatusMapping;
 
    private EmailTemplateTypes(int code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }
 
    public static EmailTemplateTypes getStatus(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }
 
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, EmailTemplateTypes>();
        for (EmailTemplateTypes s : values()) {
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
