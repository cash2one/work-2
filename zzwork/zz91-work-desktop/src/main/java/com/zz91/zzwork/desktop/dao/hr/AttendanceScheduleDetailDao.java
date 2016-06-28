package com.zz91.zzwork.desktop.dao.hr;

import java.util.Date;
import java.util.List;

import com.zz91.zzwork.desktop.domain.hr.AttendanceScheduleDetail;
import com.zz91.zzwork.desktop.domain.hr.AttendanceScheduleDetailSearch;

public interface AttendanceScheduleDetailDao {

	public Integer count(Integer scheduleId, Date gmtMonth);
	
	public Integer insert(AttendanceScheduleDetail detail);
	
	public Integer deleteSchedule(Integer scheduleId, Date gmtMonth);
	
	public List<AttendanceScheduleDetail> queryDefault(AttendanceScheduleDetailSearch search);
}
