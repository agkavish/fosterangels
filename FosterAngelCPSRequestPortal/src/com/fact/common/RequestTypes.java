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
public enum RequestTypes {
	
	CPS (1, "CPS", "CPS Request"),
	OTHER (2, "OTHER", "Non CPS Request");
	
	private int code;
    private String label;
    private String description;
	
	 /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, RequestTypes> codeToStatusMapping;
 
    private RequestTypes(int code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }
 
    public static RequestTypes getStatus(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }
 
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, RequestTypes>();
        for (RequestTypes s : values()) {
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
