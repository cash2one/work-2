/**
 * 
 */
package com.zz91.zzwork.desktop.dto.hr;

import java.io.Serializable;
import java.util.Map;

import com.zz91.zzwork.desktop.domain.hr.AttendanceSchedule;

/**
 * @author mays
 *
 */
public class AttendanceScheduleDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AttendanceSchedule schedule;
	private Map<Integer, Integer> monthCount;
	
	public AttendanceSchedule getSchedule() {
		return schedule;
	}
	public void setSchedule(AttendanceSchedule schedule) {
		this.schedule = schedule;
	}
	public Map<Integer, Integer> getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Map<Integer, Integer> monthCount) {
		this.monthCount = monthCount;
	}
	
}
