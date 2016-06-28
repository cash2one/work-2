package com.zz91.zzwork.desktop.dao.hr;


import java.util.Date;
import java.util.List;

import com.zz91.zzwork.desktop.domain.hr.Attendance;
import com.zz91.zzwork.desktop.dto.PageDto;


public interface AttendanceDao {
 
	public Integer insert(Attendance attendance);
	public Integer deleteAttendance(Date from, Date to, Integer scheduleId);
	public List<Attendance> queryAttendance(String name, String code, Date from, Date to, PageDto<Attendance> page);
	public Integer queryAttendanceCount(String name, String code, Date from, Date to);
//	public List<Attendance> queryByGmtWork(Date from, Date to);
	public List<Attendance> queryAttendancesByWork(Date from, Date to, String code, Integer scheduleId);
}
 
