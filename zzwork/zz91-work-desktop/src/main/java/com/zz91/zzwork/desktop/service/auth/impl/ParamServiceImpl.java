/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-6
 */
package com.zz91.zzwork.desktop.service.auth.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.util.domain.Param;
import com.zz91.zzwork.desktop.dao.auth.ParamDao;
import com.zz91.zzwork.desktop.service.auth.ParamService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-6
 */
@Component("paramService")
public class ParamServiceImpl implements ParamService {

	@Resource
	private ParamDao paramDao;
	
	@Override
	public List<Param> queryParam() {
		
		return paramDao.queryParam();
	}

}
