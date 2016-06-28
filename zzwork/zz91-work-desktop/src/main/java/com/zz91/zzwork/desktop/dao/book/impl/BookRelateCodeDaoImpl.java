package com.zz91.zzwork.desktop.dao.book.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.zzwork.desktop.dao.BaseDao;
import com.zz91.zzwork.desktop.dao.book.BookRelateCodeDao;
import com.zz91.zzwork.desktop.domain.book.BookRelateCode;
import com.zz91.zzwork.desktop.dto.PageDto;

@Component("bookRelateCodeDao")
public class BookRelateCodeDaoImpl  extends BaseDao implements BookRelateCodeDao{

	final static String SQL_FIX = "bookRelateCode";
	
	@Override
	public Integer insert(BookRelateCode bookRelateCode) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_FIX, "insert"), bookRelateCode);
	}

	@Override
	public Integer queryCountList(BookRelateCode bookRelateCode) {
		Map<String, Object > map = new HashMap<String, Object>();
		map.put("bookRelateCode", bookRelateCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_FIX, "queryCountList"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookRelateCode> queryList(BookRelateCode bookRelateCode,
			PageDto<BookRelateCode> page) {
		Map<String, Object > map = new HashMap<String, Object>();
		map.put("bookRelateCode", bookRelateCode);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer update(BookRelateCode bookRelateCode) {
		return getSqlMapClientTemplate().update(buildId(SQL_FIX, "update"), bookRelateCode);
	}

}
