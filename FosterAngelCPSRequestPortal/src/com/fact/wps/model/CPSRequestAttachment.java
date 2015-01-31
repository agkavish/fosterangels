/**
 * 
 */
package com.fact.wps.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * @author kagarwal
 * 
 */
@PersistenceCapable(detachable = "true")
public class CPSRequestAttachment implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String fileKey;

	/**
	 * @return the fileKey
	 */
	public String getFileKey() {
		return fileKey;
	}

	/**
	 * @param fileKey the fileKey to set
	 */
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param string
	 *            the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	@Persistent
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cwRequestKey
	 */
	public String getCwRequestKey() {
		return cwRequestKey;
	}

	/**
	 * @param reqkey
	 *            the cwRequestKey to set
	 */
	public void setCwRequestKey(String reqkey) {
		this.cwRequestKey = reqkey;
	}

	@Persistent
	private String cwRequestKey;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CPSReqeustAttachment [key=").append(key)
				.append(", fileKey=").append(fileKey).append(", name=")
				.append(name).append(", cwRequestKey=").append(cwRequestKey)
				.append("]");
		return builder.toString();
	}

}
