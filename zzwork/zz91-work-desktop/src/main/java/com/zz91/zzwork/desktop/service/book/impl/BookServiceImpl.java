package com.zz91.zzwork.desktop.service.book.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.zzwork.desktop.dao.book.BookDao;
import com.zz91.zzwork.desktop.dao.book.BookRelateCodeDao;
import com.zz91.zzwork.desktop.domain.book.Book;
import com.zz91.zzwork.desktop.domain.book.BookDto;
import com.zz91.zzwork.desktop.domain.book.BookRelateCode;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.service.book.BookService;

@Service("bookService")
public class BookServiceImpl implements BookService {

	@Resource
	private BookDao bookDao;
	@Resource
	private BookRelateCodeDao bookRelateCodeDao;

	@Override
	public Integer insert(Book book,String code) {
		Integer i =bookDao.insert(book);
		if(i>0){
			BookRelateCode bookRelateCode = new BookRelateCode();
			bookRelateCode.setBookId(i);
			bookRelateCode.setCode(code);
			bookRelateCode.setIsOut("0");
			bookRelateCodeDao.insert(bookRelateCode);
		}
		return i;
	}

	@Override
	public PageDto<BookDto> pageBook(Book book, PageDto<BookDto> page) {
		List<Book> list = bookDao.queryList(book, page);
		List<BookDto> nlist = new ArrayList<BookDto>();
		page.setTotalRecords(bookDao.queryCountList(book));
		for (Book obj:list) {
			BookDto dto = new BookDto();
			dto.setBook(obj);
			PageDto<BookRelateCode> relatePage = new PageDto<BookRelateCode>();
			BookRelateCode bookRelateCode = new BookRelateCode();
			bookRelateCode.setBookId(obj.getId());
			dto.setRelateList(bookRelateCodeDao.queryList(bookRelateCode, relatePage));
			nlist.add(dto);
		}
		page.setRecords(nlist);
		return page;
	}

	@Override
	public Integer update(Book book) {
		if (book == null || book.getId() == null) {
			return 0;
		}
		return bookDao.update(book);
	}

	@Override
	public BookDto queryOne(Integer id) {
		if(id<1){
			return null;
		}
		Book book = bookDao.queryOne(id);
		if(book==null){
			return null;
		}
		BookDto dto = new BookDto();
		dto.setBook(book);
		PageDto<BookRelateCode> relatePage = new PageDto<BookRelateCode>();
		BookRelateCode bookRelateCode = new BookRelateCode();
		bookRelateCode.setBookId(book.getId());
		dto.setRelateList(bookRelateCodeDao.queryList(bookRelateCode, relatePage));
		return dto;
	}

}
