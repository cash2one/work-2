package com.zz91.zzwork.desktop.controller.hr;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.auth.SessionUser;
import com.zz91.zzwork.desktop.controller.BaseController;
import com.zz91.zzwork.desktop.domain.hr.AttendanceSchedule;
import com.zz91.zzwork.desktop.dto.ExtResult;
import com.zz91.zzwork.desktop.dto.hr.AttendanceScheduleDto;
import com.zz91.zzwork.desktop.service.hr.AttendanceScheduleService;
import com.zz91.zzwork.desktop.util.DesktopConst;

@Controller
public class AttendanceScheduleController extends BaseController {

	@Resource
	private AttendanceScheduleService attendanceScheduleService;

	@RequestMapping
	public void index(HttpServletRequest request,Map<String, Object> out,
		Integer year, Integer isuse) {
		if(isuse==null){
			isuse=DesktopConst.ISUSE_TRUE;
		}
		
		if(year==null){
			Calendar.getInstance().setTime(new Date());
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		
		out.put("year", year);
		
		List<AttendanceScheduleDto> list=attendanceScheduleService.querySchedule(isuse, year);
		out.put("list", list);
		
	}

	@RequestMapping
	public ModelAndView create(HttpServletRequest request, Map<String, Object> out,
			AttendanceSchedule schedule){
		
		SessionUser user=getCachedUser(request);
		schedule.setCreatedBy(user.getAccount());
		schedule.setModifiedBy(user.getAccount());
		
		Integer i= attendanceScheduleService.create(schedule);
		
		ExtResult result=new ExtResult();
		if(i!=null&&i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView update(HttpServletRequest request, Map<String, Object> out,
			AttendanceSchedule schedule){
		
		SessionUser user=getCachedUser(request);
		schedule.setModifiedBy(user.getAccount());
		
		
		Integer i= attendanceScheduleService.update(schedule);
		
		ExtResult result=new ExtResult();
		if(i!=null&&i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateStatus(HttpServletRequest request, Map<String, Object> out,
			Integer id, Integer isuse){
		Integer i=attendanceScheduleService.updateStatus(id, isuse);
		
		ExtResult result=new ExtResult();
		if(i!=null&&i.intValue()>0){
			result.setSuccess(true);
		}
		
		return printJson(result, out);
	}
}
