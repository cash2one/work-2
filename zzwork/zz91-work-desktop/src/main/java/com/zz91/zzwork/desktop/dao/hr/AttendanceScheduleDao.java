package com.zz91.zzwork.desktop.dao.hr;

import java.util.List;

import com.zz91.zzwork.desktop.domain.hr.AttendanceSchedule;

public interface AttendanceScheduleDao {

	public List<AttendanceSchedule> queryDefault(Integer isuse);
	
	public Integer insert(AttendanceSchedule schedule);
	
	public Integer update(AttendanceSchedule schedule);
	
	public Integer updateStatus(Integer id, Integer isuse);
	
	public String queryName(Integer id);

}
