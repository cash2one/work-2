/**
 * @author kongsj
 * @date 2014年7月1日
 * 
 */
package com.zz91.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
//		Enumeration params = request.getParameterNames();
//		String param = "";
//		while (params.hasMoreElements()) {
//			param = (String) params.nextElement();
//			String value = xssRequest.getParameter(param);
//			System.out.print(value);
//			xssRequest.setAttribute(param, value);
//			request.setAttribute(param, value);
//		}
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {

	}

}
