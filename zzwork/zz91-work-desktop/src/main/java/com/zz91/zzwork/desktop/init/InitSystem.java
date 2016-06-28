/**
 * Copyright 2010 YYSoft
 * All right reserved.
 * Created on 2010-5-9
 */
package com.zz91.zzwork.desktop.init;

import java.util.List;

import javax.annotation.Resource;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.domain.Param;
import com.zz91.util.param.ParamUtils;
import com.zz91.zzwork.desktop.service.auth.ParamService;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class InitSystem {

	@Resource
	private ParamService paramService;
	
	public void startup(){
		List<Param> paramList = paramService.queryParam();
		ParamUtils.getInstance().init(paramList, null);
		
		MemcachedUtils.getInstance().init();
	}
	
	public void shutdown(){
		MemcachedUtils.getInstance().shutdownClient();
	}

}
