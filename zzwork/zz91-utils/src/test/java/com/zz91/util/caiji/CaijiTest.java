package com.zz91.util.caiji;

import com.zz91.util.http.HttpUtils;

/**
 *	author:kongsj
 *	date:2013-5-29
 */
public class CaijiTest {
	final static String URL = "http://price.zz91.com/priceDetails_461396_metal.htm";
	public static void main(String[] args) {
		String html = "";
		html = HttpUtils.getInstance().httpGet(URL, HttpUtils.CHARSET_UTF8);
		Integer start = html.indexOf("<div class=\"content\">");
		Integer end = html.indexOf("<div class=\"thisLabel\">");
		String content = html.substring(start, end);
		System.out.println(content);
	}
}
