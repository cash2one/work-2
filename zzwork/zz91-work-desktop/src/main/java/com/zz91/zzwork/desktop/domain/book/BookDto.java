package com.zz91.zzwork.desktop.domain.book;

import java.util.List;


public class BookDto {
	private Book book;
	private List<BookRelateCode> relateList;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public List<BookRelateCode> getRelateList() {
		return relateList;
	}
	public void setRelateList(List<BookRelateCode> relateList) {
		this.relateList = relateList;
	}

}
