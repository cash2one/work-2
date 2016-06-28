/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-9
 */
package com.zz91.zzwork.desktop.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.zzwork.desktop.domain.auth.AuthRole;
import com.zz91.zzwork.desktop.domain.staff.Staff;
import com.zz91.zzwork.desktop.dto.ExtResult;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.dto.staff.StaffDto;
import com.zz91.zzwork.desktop.service.auth.AuthRoleService;
import com.zz91.zzwork.desktop.service.staff.StaffService;
import com.zz91.zzwork.desktop.util.DesktopConst;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-9
 */
@Controller
public class StaffController extends BaseController{
	
	@Resource
	private AuthRoleService authRoleService;
	@Resource
	private StaffService staffService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryStaff(HttpServletRequest request, Map<String, Object> out, PageDto<StaffDto> page, Staff staff){
		page = staffService.pageStaff(staff, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView updateStaff(HttpServletRequest request, Map<String, Object> out, Staff staff, String password, String roleArr, String gmtEntryDate){
		try {
			staff.setGmtEntry(DateUtil.getDate(gmtEntryDate, DesktopConst.DATE_FORMAT));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer i=staffService.updateStaff(staff, password, roleArr);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createStaff(HttpServletRequest request, Map<String, Object> out, Staff staff, String password, String roleArr, String gmtEntryDate){
		try {
			staff.setGmtEntry(DateUtil.getDate(gmtEntryDate, DesktopConst.DATE_FORMAT));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer i = staffService.createStaff(staff, staff.getAccount(), password, StringUtils.StringToIntegerArray(roleArr));
		
		if(StringUtils.isEmail(staff.getEmail())){
			Map<String, Object> dataMap=new HashMap<String, Object>();
			dataMap.put("staff", staff);
			dataMap.put("pwd", password);
			MailUtil.getInstance().sendMail(staff.getName()+"，您的工作平台账号开通啦，快来看一下吧！",
					staff.getEmail(), null, null, "zz91",
					"zzwork-staff", dataMap, MailUtil.PRIORITY_DEFAULT);
		}
		
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView regular(HttpServletRequest request, Map<String, Object> out, String note, Integer staffId, String gmtLeftDate){
		Date gmtLeft = null;
		try {
			gmtLeft=DateUtil.getDate(gmtLeftDate, DesktopConst.DATE_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer i=staffService.regular(staffId, note, gmtLeft);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView left(HttpServletRequest request, Map<String, Object> out, String note, String gmtLeftDate, Integer staffId){
		Date gmtLeft = null;
		try {
			gmtLeft=DateUtil.getDate(gmtLeftDate, DesktopConst.DATE_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer i=staffService.staffTurnover(staffId, note, gmtLeft);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryRole(HttpServletRequest request, Map<String, Object> out){
		PageDto<AuthRole> page=new PageDto<AuthRole>();
		page.setRecords(authRoleService.queryRole());
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneStaff(HttpServletRequest request, Map<String, Object> out, String account){
		StaffDto staff=staffService.queryStaffByAccount(account);
		PageDto<StaffDto> page=new PageDto<StaffDto>();
		List<StaffDto> list=new ArrayList<StaffDto>();
		list.add(staff);
		page.setRecords(list);
		return printJson(page, out);
	}
}
