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
public enum ProfileStatusTypes {
	
	NEW (-1, "New", "The new user profile submited"),
	PENDINGVERIFICATION (0, "PendingVerification", "User email verification pending"),
	ACTIVE(1, "Active", "Active"),
	DISABLED(2, "Disabled", "Disabled");	
	
	private int code;
    private String label;
    private String description;
	
	 /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, ProfileStatusTypes> codeToStatusMapping;
 
    private ProfileStatusTypes(int code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }
 
    public static ProfileStatusTypes getStatus(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }
 
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, ProfileStatusTypes>();
        for (ProfileStatusTypes s : values()) {
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
