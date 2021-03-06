/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-5
 */
package com.zz91.zzwork.desktop.controller.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.auth.SessionUser;
import com.zz91.zzwork.desktop.controller.BaseController;
import com.zz91.zzwork.desktop.domain.staff.SchedulerReport;
import com.zz91.zzwork.desktop.dto.ExtResult;
import com.zz91.zzwork.desktop.service.staff.schedulerReportService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-5
 */
@Controller
public class ReportController extends BaseController{
	
	@Resource
	private schedulerReportService schedulerReportService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView compose(HttpServletRequest request, Map<String, Object> out){
		//当前年份前后五年
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(new Date());
		out.put("year", calendar.get(Calendar.YEAR));
		out.put("week", calendar.get(Calendar.WEEK_OF_YEAR));
		return null;
	}
	
	@RequestMapping
	public ModelAndView createReport(HttpServletRequest request,Map<String, Object> out,
			SchedulerReport report){
		
		SessionUser sessionUser=getCachedUser(request);
		report.setAccount(sessionUser.getAccount());
		report.setDeptCode(sessionUser.getDeptCode());
		
		Integer i =schedulerReportService.createReport(report);
		
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createReportEvent(HttpServletRequest request,Map<String, Object> out,
			Integer reportId,Integer eventId){
		
		Integer i=schedulerReportService.createReportEvent(reportId, eventId);
		
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setData(i);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
