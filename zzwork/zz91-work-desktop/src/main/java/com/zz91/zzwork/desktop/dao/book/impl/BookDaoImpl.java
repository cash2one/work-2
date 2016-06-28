package com.zz91.zzwork.desktop.dao.book.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.zzwork.desktop.dao.BaseDao;
import com.zz91.zzwork.desktop.dao.book.BookDao;
import com.zz91.zzwork.desktop.domain.book.Book;
import com.zz91.zzwork.desktop.domain.book.BookDto;
import com.zz91.zzwork.desktop.dto.PageDto;

@Repository("bookDao")
public class BookDaoImpl extends BaseDao implements BookDao {

	final static String SQL_PREFIX = "book";

	@Override
	public Integer insert(Book book) {
		return (Integer) getSqlMapClientTemplate().insert(
				buildId(SQL_PREFIX, "insert"), book);
	}

	@Override
	public Integer queryCountList(Book book) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("book", book);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryCountList"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> queryList(Book book, PageDto<BookDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("book", book);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				buildId(SQL_PREFIX, "queryList"), map);
	}

	@Override
	public Integer update(Book book) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "update"),
				book);
	}

	@Override
	public Book queryOne(Integer id) {
		return (Book) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOne"), id);
	}

}
