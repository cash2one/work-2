/**
 * 
 */
package com.zz91.zzwork.desktop.service.hr.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.zzwork.desktop.dao.hr.AttendanceAnalysisDao;
import com.zz91.zzwork.desktop.domain.hr.AttendanceAnalysis;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.service.hr.AttendanceAnalysisService;

/**
 * @author mays
 *
 */
@Component("attendanceAnalysisService")
public class AttendanceAnalysisServiceImpl implements AttendanceAnalysisService {
	
	@Resource
	private AttendanceAnalysisDao attendanceAnalysisDao;

	@Override
	public PageDto<AttendanceAnalysis> pageAnalysis(String name, String code,
			Date month, PageDto<AttendanceAnalysis> page) {
		
		if(page.getSort()==null){
			page.setSort("id");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		
		page.setRecords(attendanceAnalysisDao.queryAnalysis(name, code, month, page));
		page.setTotalRecords(attendanceAnalysisDao.queryAnalysisCount(name, code, month));
		
		return page;
	}

	@Override
	public Integer updateAnalysis(AttendanceAnalysis analysis) {
		return attendanceAnalysisDao.updateAnalysis(analysis);
	}

	@Override
	public List<AttendanceAnalysis> queryAnalysisByMonth(Date month){
		return attendanceAnalysisDao.queryAnalysisByMonth(month);
	}

	

}
