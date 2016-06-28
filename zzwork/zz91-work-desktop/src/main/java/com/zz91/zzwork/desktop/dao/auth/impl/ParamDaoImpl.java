/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-6
 */
package com.zz91.zzwork.desktop.dao.auth.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.util.domain.Param;
import com.zz91.zzwork.desktop.dao.BaseDao;
import com.zz91.zzwork.desktop.dao.auth.ParamDao;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-6
 */
@Component("paramDao")
public class ParamDaoImpl extends BaseDao implements ParamDao {
	
	@SuppressWarnings("unchecked")
	public List<Param> queryParam(){
		return getSqlMapClientTemplate().queryForList(buildId("param", "queryParam"));
	}
}
