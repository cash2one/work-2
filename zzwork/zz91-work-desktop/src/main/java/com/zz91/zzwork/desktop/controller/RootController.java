/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package com.zz91.zzwork.desktop.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.auth.AuthConst;
import com.zz91.util.auth.AuthMenu;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.lang.StringUtils;
import com.zz91.zzwork.desktop.domain.bs.Bs;
import com.zz91.zzwork.desktop.domain.staff.Feedback;
import com.zz91.zzwork.desktop.dto.ExtResult;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.dto.staff.StaffDto;
import com.zz91.zzwork.desktop.service.bs.BsService;
import com.zz91.zzwork.desktop.service.staff.FeedbackService;
import com.zz91.zzwork.desktop.service.staff.StaffService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-4
 */
@Controller
public class RootController extends BaseController {
	
	@Resource
	private BsService bsService;
	@Resource
	private StaffService staffService;
	@Resource
	private FeedbackService feedbackService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
//		boolean result=AuthUtils.getInstance().authorizeRight("test", request, null);
		out.put("staffName", getCachedUser(request).getName());
//		out.put("staffName", result);
		return null;
	}
	
	@RequestMapping
	public ModelAndView error(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView forbiden(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView login(Map<String, Object> out, HttpServletRequest request, String go){
		if(StringUtils.isNotEmpty(go)){
			out.put("gourl", bsService.queryUrl(go));
		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView profile(Map<String, Object> out, HttpServletRequest request){
		StaffDto staff=staffService.queryStaffByAccount(getCachedUser(request).getAccount());
		out.put("profile",staff);
		return null;
	}
	
	@RequestMapping
	public ModelAndView welcome(Map<String, Object> out, HttpServletRequest request){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView checkuser(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response, String account, String password){
		System.out.println(AuthConst.API_HOST);
		System.out.println(AuthConst.SSO_DOMAIN);
		SessionUser sessionUser = AuthUtils.getInstance().validateUser(response, account, password, AuthConst.PROJECT_CODE, AuthConst.PROJECT_PASSWORD);
		ExtResult result = new ExtResult();
		if(sessionUser!=null){
			setSessionUser(request, sessionUser);
			result.setSuccess(true);
		}else{
			result.setData("用户名或者密码写错了，检查下大小写是否都正确了，再试一次吧 :)");
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView logout(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response){
		AuthUtils.getInstance().logout(request, response, null);
		return new ModelAndView("redirect:login.htm");
	}
	
	@RequestMapping
	public ModelAndView mybs(String types, Map<String, Object> out, HttpServletRequest request){
		SessionUser sessionUser = getCachedUser(request);
		PageDto<Bs> page = new PageDto<Bs>();
		
		page.setRecords(bsService.queryMyBs(sessionUser.getAccount(),types));
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView mymenu(String parentCode, Map<String, Object> out, HttpServletRequest request){
		if(parentCode==null){
			parentCode="";
		}
		SessionUser sessionUser = getCachedUser(request);
		List<AuthMenu> list = AuthUtils.getInstance().queryMenuByParent(parentCode, AuthConst.PROJECT_CODE, sessionUser.getAccount());
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView changePassword(Map<String, Object> out, HttpServletRequest request, String originalPassword, String newPassword, String verifyPassword){
		Integer i= staffService.changePassword(getCachedUser(request).getAccount(), originalPassword, newPassword, verifyPassword);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView feedback(Map<String, Object> out, HttpServletRequest request, Feedback feedback){
		feedback.setAccount(getCachedUser(request).getAccount());
		Integer i= feedbackService.feedback(feedback);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
