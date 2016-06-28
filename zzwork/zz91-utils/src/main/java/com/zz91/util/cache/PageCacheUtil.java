/**
 * 
 */
package com.zz91.util.cache;

import javax.servlet.http.HttpServletResponse;

/**
 * @author mays (mays@zz91.net)
 *
 */
public class PageCacheUtil {
	
	final static String HEADER_CACHE_CONTROL="Cache-Control";
	final static String HEADER_PRAGMA="Pragma";
	final static String HEADER_EXPIRES="Expires";
	
	public final static long MAX_AGE_DAY=86400;
	public final static long MAX_AGE_HOUR=3600;
	public final static long MAX_AGE_MIN=60;
	
	final static String PRIVATE_NO_CACHE="private,max-age=0,no-cache";
	final static String NO_CACHE="no-cache";
	final static String MAX_AGE="max-age=";

	public static void setNoCache(HttpServletResponse response){
		/*--This is used for HTTP 1.1 --*/
		response.setHeader(HEADER_CACHE_CONTROL, PRIVATE_NO_CACHE);
		/*--This is used for HTTP 1.0 --*/
		response.setHeader(HEADER_PRAGMA,NO_CACHE);
		/*---- This is used to prevents caching at the proxy server */
		response.setDateHeader (HEADER_EXPIRES, 0);
	}
	
	public static void setNoCDNCache(HttpServletResponse response){
		response.setHeader(HEADER_CACHE_CONTROL, PRIVATE_NO_CACHE);
	}
	
	public static void setCDNCache(HttpServletResponse response, long timeout){
		response.setHeader(HEADER_CACHE_CONTROL, MAX_AGE+timeout);
	}
	
	public static void setStatus(HttpServletResponse response,Integer status){
		response.setStatus(status);
	}
	
}
