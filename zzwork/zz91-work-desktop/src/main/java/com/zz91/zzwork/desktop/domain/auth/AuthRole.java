package com.zz91.zzwork.desktop.domain.auth;

import java.util.Date;

/**
 * AuthRole generated by MyEclipse Persistence Tools
 */

public class AuthRole implements java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;

	// Constructors

	/** default constructor */
	public AuthRole() {
	}

	/** minimal constructor */
	public AuthRole(Integer id) {
		this.id = id;
	}

	// Property accessors

	/**
	 * @param name
	 * @param remark
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public AuthRole(String name, String remark, Date gmtCreated,
			Date gmtModified) {
		super();
		this.name = name;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}

	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}