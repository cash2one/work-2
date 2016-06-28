/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package com.zz91.zzwork.desktop.service.staff.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.util.Assert;
import com.zz91.zzwork.desktop.dao.staff.FeedbackDao;
import com.zz91.zzwork.desktop.domain.staff.Feedback;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.service.staff.FeedbackService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
@Component("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	@Resource
	private FeedbackDao feedbackDao;
	
	@Override
	public Integer dealImpossible(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return feedbackDao.updateStatus(id, FeedbackDao.STATUS_IMPOSSIBLE);
	}

	@Override
	public Integer dealNothing(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return feedbackDao.updateStatus(id, FeedbackDao.STATUS_NOTHING);
	}

	@Override
	public Integer dealSuccess(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return feedbackDao.updateStatus(id, FeedbackDao.STATUS_SUCCESS);
	}

	@Override
	public Integer deleteFeedback(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return feedbackDao.deleteFeedback(id);
	}

	@Override
	public Integer feedback(Feedback feedback) {
		if(feedback.getBsId()==null){
			feedback.setBsId(0);
		}
		if(feedback.getStatus()==null){
			feedback.setStatus(FeedbackDao.STATUS_UN);
		}
		return feedbackDao.insertFeedback(feedback);
	}

	@Override
	public PageDto<Feedback> pageFeedback(Integer status, PageDto<Feedback> page) {
		page.setRecords(feedbackDao.queryFeedback(status, page));
		page.setTotalRecords(feedbackDao.queryFeedbackCount(status));
		return page;
	}
}
