package com.zz91.zzwork.desktop.dao.hr;

import java.util.Date;
import java.util.List;

import com.zz91.zzwork.desktop.domain.hr.AttendanceAnalysis;
import com.zz91.zzwork.desktop.dto.PageDto;

public interface AttendanceAnalysisDao {
 
	public Integer insert(AttendanceAnalysis attendanceAnalysis);
	
	public Integer deleteByGmtTarget(Date gmtTarget, Integer scheduleId);
	
	public List<AttendanceAnalysis> queryAnalysis(String name, String code,
			Date month, PageDto<AttendanceAnalysis> page);
	
	public Integer queryAnalysisCount(String name, String code, Date month);
	
	public Integer updateAnalysis(AttendanceAnalysis analysis);
	
	public List<AttendanceAnalysis> queryAnalysisByMonth(Date month);
}
 
