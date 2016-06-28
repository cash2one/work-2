/**
 * 
 */
package com.zz91.zzwork.desktop.service.hr.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.zzwork.desktop.dao.hr.AttendanceScheduleDao;
import com.zz91.zzwork.desktop.dao.hr.AttendanceScheduleDetailDao;
import com.zz91.zzwork.desktop.domain.hr.AttendanceSchedule;
import com.zz91.zzwork.desktop.dto.hr.AttendanceScheduleDto;
import com.zz91.zzwork.desktop.service.hr.AttendanceScheduleService;

/**
 * @author mays
 *
 */
@Component("attendanceScheduleService")
public class AttendanceScheduleServiceImpl implements AttendanceScheduleService {

	@Resource
	private AttendanceScheduleDao attendanceScheduleDao;
	@Resource
	private AttendanceScheduleDetailDao attendanceScheduleDetailDao;
	
	@Override
	public List<AttendanceScheduleDto> querySchedule(Integer isuse, Integer year) {
		
		List<AttendanceSchedule> list=attendanceScheduleDao.queryDefault(isuse);
		
		List<AttendanceScheduleDto> result=new ArrayList<AttendanceScheduleDto>();
		
		//可优化
		
		for(AttendanceSchedule schedule:list){
			AttendanceScheduleDto dto=new AttendanceScheduleDto();
			dto.setSchedule(schedule);
			//查找各个月份的COUNT
			Map<Integer, Integer> month=new HashMap<Integer, Integer>();
			for(int i=0;i<12;i++){
				Calendar c=Calendar.getInstance();
				c.set(year, i, 1, 0, 0, 0);
				month.put(i, attendanceScheduleDetailDao.count(schedule.getId(), c.getTime()));
			}
			dto.setMonthCount(month);
			result.add(dto);
		}
		return result;
	}

	@Override
	public Integer create(AttendanceSchedule schedule) {
		return attendanceScheduleDao.insert(schedule);
	}

	@Override
	public Integer update(AttendanceSchedule schedule) {
		return attendanceScheduleDao.update(schedule);
	}
	
	@Override
	public Integer updateStatus(Integer id, Integer isuse) {
		return attendanceScheduleDao.updateStatus(id, isuse);
	}

	@Override
	public String queryName(Integer id) {
		if(id==null){
			return "";
		}
		return attendanceScheduleDao.queryName(id);
	}

	@Override
	public List<AttendanceSchedule> queryScheduleOnly(Integer isuse) {
		return attendanceScheduleDao.queryDefault(isuse);
	}
	
	

}
