/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-9
 */
package com.zz91.zzwork.desktop.controller.auth;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.zzwork.desktop.controller.BaseController;
import com.zz91.zzwork.desktop.domain.auth.AuthRole;
import com.zz91.zzwork.desktop.dto.ExtResult;
import com.zz91.zzwork.desktop.dto.ExtTreeDto;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.service.auth.AuthRoleService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-9
 */
@Controller
public class RoleController extends BaseController{
	
	@Resource 
	private AuthRoleService authRoleService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView rigthChild(HttpServletRequest request, Map<String, Object> out, String parentCode, Integer roleId){
		List<ExtTreeDto> rightTree = authRoleService.queryRightTreeNode(parentCode, roleId);
		return printJson(rightTree, out);
	}
	
	@RequestMapping
	public ModelAndView updateRole(HttpServletRequest request, Map<String, Object> out, AuthRole role){
		Integer i=authRoleService.updateRole(role);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createRole(HttpServletRequest request, Map<String, Object> out, AuthRole role){
		Integer i=authRoleService.createRole(role);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView listRole(HttpServletRequest request, Map<String, Object> out){
		PageDto<AuthRole> page=new PageDto<AuthRole>();
		page.setRecords(authRoleService.queryRole());
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView deleteRole(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i=authRoleService.deleteRole(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updateRoleRight(HttpServletRequest request, Map<String, Object> out, Integer roleId, Integer rightId, Boolean checked){
		Integer i = authRoleService.updateRoleRight(roleId, rightId, checked);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
