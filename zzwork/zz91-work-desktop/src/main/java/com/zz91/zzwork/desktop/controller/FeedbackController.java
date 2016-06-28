/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-9
 */
package com.zz91.zzwork.desktop.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.zzwork.desktop.domain.staff.Feedback;
import com.zz91.zzwork.desktop.dto.ExtResult;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.service.staff.FeedbackService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-9
 */
@Controller
public class FeedbackController extends BaseController{

	@Resource
	private FeedbackService feedbackService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryFeedback(HttpServletRequest request, Map<String, Object> out, Integer status, PageDto<Feedback> page){
		page.setSort("id");
		page.setDir("desc");
		page = feedbackService.pageFeedback(status, page);
		
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView dealSuccess(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = feedbackService.dealSuccess(id);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView dealNothing(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = feedbackService.dealNothing(id);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView dealImpossible(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = feedbackService.dealImpossible(id);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteFeedback(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = feedbackService.deleteFeedback(id);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
