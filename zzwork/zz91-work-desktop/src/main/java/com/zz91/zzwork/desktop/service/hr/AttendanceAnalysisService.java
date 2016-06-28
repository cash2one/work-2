package com.zz91.zzwork.desktop.service.hr;


import java.util.Date;
import java.util.List;

import com.zz91.zzwork.desktop.domain.hr.AttendanceAnalysis;
import com.zz91.zzwork.desktop.dto.PageDto;

public interface AttendanceAnalysisService {
	
	public PageDto<AttendanceAnalysis> pageAnalysis(String name, String code, Date month, PageDto<AttendanceAnalysis> page );
	
	public Integer updateAnalysis(AttendanceAnalysis analysis);
	
	public List<AttendanceAnalysis> queryAnalysisByMonth(Date month);
	
}
 
