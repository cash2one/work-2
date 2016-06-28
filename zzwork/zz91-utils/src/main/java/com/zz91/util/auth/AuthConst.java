/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-7
 */
package com.zz91.util.auth;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-7
 */
public class AuthConst {
	public static String PROJECT_CODE="";
	public static String PROJECT_PASSWORD="";

	public static String API_HOST="http://work.zz91.com/api";
	public static String SSO_DOMAIN="zz91.com";

	public final static String TICKET_KEY="zz91ssoticketkey";
	
	private String projectCode;
	private String projectPassword;
	private String api;
	private String domain;
	
	public void startup(){
		PROJECT_CODE = projectCode;
		PROJECT_PASSWORD = projectPassword;
		API_HOST = api;
		SSO_DOMAIN = domain;
	}
	
	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}
	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * @return the projectPassword
	 */
	public String getProjectPassword() {
		return projectPassword;
	}
	/**
	 * @param projectPassword the projectPassword to set
	 */
	public void setProjectPassword(String projectPassword) {
		this.projectPassword = projectPassword;
	}
	/**
	 * @return the api
	 */
	public String getApi() {
		return api;
	}
	/**
	 * @param api the api to set
	 */
	public void setApi(String api) {
		this.api = api;
	}
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
	
}
