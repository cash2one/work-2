/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package com.zz91.zzwork.desktop.dao.staff.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.zzwork.desktop.dao.BaseDao;
import com.zz91.zzwork.desktop.dao.staff.FeedbackDao;
import com.zz91.zzwork.desktop.domain.staff.Feedback;
import com.zz91.zzwork.desktop.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
@Component("feedbackDao")
public class FeedbackDaoImpl extends BaseDao implements FeedbackDao {

	final static String SQL_PREFIX="feedback";
	
	@Override
	public Integer deleteFeedback(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteFeedback"),id);
	}

	@Override
	public Integer insertFeedback(Feedback feedback) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertFeedback"), feedback);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Feedback> queryFeedback(Integer status, PageDto<Feedback> page) {
		Map<String,Object> root =new HashMap<String, Object>();
		root.put("status", status);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryFeedback"),root);
	}

	@Override
	public Integer queryFeedbackCount(Integer status) {
		Map<String,Object> root =new HashMap<String, Object>();
		root.put("status", status);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryFeedbackCount"),root);
	}

	@Override
	public Integer updateStatus(Integer id, Integer status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id",id);
		root.put("status", status);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStatus"), root);
	}

}
