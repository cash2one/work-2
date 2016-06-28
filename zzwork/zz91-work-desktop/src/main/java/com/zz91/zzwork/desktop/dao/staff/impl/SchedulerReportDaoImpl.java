package com.zz91.zzwork.desktop.dao.staff.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.zzwork.desktop.dao.BaseDao;
import com.zz91.zzwork.desktop.dao.staff.SchedulerReportDao;
import com.zz91.zzwork.desktop.domain.staff.SchedulerReport;
@Component("schedulerReportDao")
public class SchedulerReportDaoImpl extends BaseDao implements SchedulerReportDao{
	
	final static String SQL_PREFIX="schedulerReport";
	
	@Override
	public Integer insertReport(SchedulerReport report) {
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertReport"), report);
	}

	@Override
	public Integer insertReportEvent(Integer reportId, Integer eventId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("reportId", reportId);
		root.put("eventId", eventId);
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertReportEvent"), root);
	}

	@Override
	public SchedulerReport queryOneReport(Integer id) {
		
		return (SchedulerReport) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneReport"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerReport> queryReport(String year, Integer week,
			String account, String deptCode) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("year", year);
		root.put("week", week);
//		root.put("account", account);
//		root.put("deptCode", deptCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryReport"), root);
	}

}
