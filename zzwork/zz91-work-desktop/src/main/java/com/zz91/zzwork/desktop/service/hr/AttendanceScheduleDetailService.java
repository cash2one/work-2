package com.zz91.zzwork.desktop.service.hr;

import java.util.Date;
import java.util.List;

import com.zz91.zzwork.desktop.domain.hr.AttendanceScheduleDetailSearch;
import com.zz91.zzwork.desktop.dto.hr.AttendanceScheduleDetailDto;

public interface AttendanceScheduleDetailService {

	public void buildSchedule(Integer scheduleId, Date month, Integer[] day, 
			String[] workf, String[] workt, String createdBy);
	
	public List<AttendanceScheduleDetailDto> buildInitSchedule(AttendanceScheduleDetailSearch search);
	
}
