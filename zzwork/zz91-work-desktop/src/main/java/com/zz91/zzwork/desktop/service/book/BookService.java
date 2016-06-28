package com.zz91.zzwork.desktop.service.book;


import com.zz91.zzwork.desktop.domain.book.Book;
import com.zz91.zzwork.desktop.domain.book.BookDto;
import com.zz91.zzwork.desktop.dto.PageDto;

public interface BookService {

	public Integer insert(Book book,String code);

	public Integer update(Book book);
	
	public BookDto queryOne(Integer id);

	public PageDto<BookDto> pageBook(Book book, PageDto<BookDto> page);
}
