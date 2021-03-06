/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-5
 */
package com.zz91.zzwork.desktop.service.auth.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.util.lang.StringUtils;
import com.zz91.zzwork.desktop.dao.auth.AuthRightDao;
import com.zz91.zzwork.desktop.domain.auth.AuthRight;
import com.zz91.zzwork.desktop.dto.ExtTreeDto;
import com.zz91.zzwork.desktop.service.auth.AuthRightService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-5
 */
@Component("authRightService")
public class AuthRightServiceImpl implements AuthRightService {

	@Resource
	private AuthRightDao authRightDao;
	
	@Override
	public Integer createRight(AuthRight right, String parentCode) {
		if(parentCode==null){
			parentCode = "";
		}
		String code=authRightDao.queryMaxCodeOfChild(parentCode);
		if(code!=null && code.length()>0){
			code = code.substring(parentCode.length());
			Integer codeInt=Integer.valueOf(code);
			codeInt++;
			right.setCode(parentCode+String.valueOf(codeInt));
		}else{
			right.setCode(parentCode+"1000");
		}
		
		if(right.getSort()==null){
			right.setSort(0);
		}
		
		return authRightDao.insertRight(right);
	}

	@Override
	public Integer deleteRightByCode(String code) {
		if(StringUtils.isEmpty(code) || code.length()<=0){
			return null;
		}
		
		authRightDao.deleteDeptRight(code);
		authRightDao.deleteRoleRight(code);
		return authRightDao.deleteRightByCode(code);
	}

	@Override
	public List<ExtTreeDto> queryTreeNode(String parentCode) {
		List<AuthRight> rightList = authRightDao.queryChild(parentCode);
		List<ExtTreeDto> nodeList = new ArrayList<ExtTreeDto>();
		
		if(rightList==null){
			return nodeList;
		}
		
		for(AuthRight right:rightList){
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(right.getId()));
			node.setData(right.getCode());
			node.setText(right.getName());
			Integer i = authRightDao.countChild(right.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		
		return nodeList;
	}

	@Override
	public Integer updateRight(AuthRight right) {
		
		return authRightDao.updateRight(right);
	}

	@Override
	public AuthRight queryOneRight(String code) {
		
		return authRightDao.queryOneRight(code);
	}

}
