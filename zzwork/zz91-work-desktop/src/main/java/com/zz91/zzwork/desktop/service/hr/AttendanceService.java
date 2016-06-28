package com.zz91.zzwork.desktop.service.hr;


import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zz91.zzwork.desktop.domain.hr.Attendance;
import com.zz91.zzwork.desktop.domain.hr.AttendanceScheduleDetail;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.dto.hr.WorkDay;

public interface AttendanceService {
 
	public Boolean impt(Date from, Date to ,InputStream inputStream, 
			String dateFormat, Integer scheduleId);
	
	public PageDto<Attendance> pageAttendance(String name, String code, Date from, Date to, PageDto<Attendance> page );
	
	/**
	 * <br />当月应出勤天数：统计选定工作日
	 * <br />出勤天数：工作日内有打卡记录
	 * <br />
	 * <br />请假：无法判断，由HR填写，不在统计范围内
	 * <br />其他天数：无法判断，由HR定义与填写，不在统计范围内
	 * <br />
	 * <br />未打卡：无上班/下班时间记录
	 * <br />旷工：无记录=应出勤天数-出勤天数
	 * <br />迟到：上班时间>应上班时间
	 * <br />早退：下班时间<应下班时间
	 * <br />加班：下班时间>20:00
	 * @param month
	 * @param workDay
	 */
	public void analysis(Date month);
	
	public void analysisBySchedule(Date month, Integer scheduleId, List<AttendanceScheduleDetail> detailList);
	
	public List<WorkDay> buildWorkday(Date month, Integer[] day, String[] workf, String[] workt);
	
	public List<WorkDay> buildworkDays(Date month);
	
	public Map<Long, List<Date>> queryAttendData(String code, Date month, Integer scheduleId);
}
 
