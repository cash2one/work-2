package com.zz91.zzwork.desktop.dao.book;

import java.util.List;

import com.zz91.zzwork.desktop.domain.book.Book;
import com.zz91.zzwork.desktop.domain.book.BookDto;
import com.zz91.zzwork.desktop.dto.PageDto;


public interface BookDao {

	public Integer insert(Book book);

	public List<Book> queryList(Book book,PageDto<BookDto>page);

	public Integer queryCountList(Book book);

	public Integer update(Book book);
	
	public Book queryOne(Integer id);

}
