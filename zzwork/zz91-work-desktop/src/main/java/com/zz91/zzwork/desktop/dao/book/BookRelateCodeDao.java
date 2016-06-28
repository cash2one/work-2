package com.zz91.zzwork.desktop.dao.book;

import java.util.List;

import com.zz91.zzwork.desktop.domain.book.BookRelateCode;
import com.zz91.zzwork.desktop.dto.PageDto;

public interface BookRelateCodeDao {
	
	public Integer insert(BookRelateCode bookRelateCode);

	public List<BookRelateCode> queryList(BookRelateCode book,PageDto<BookRelateCode> page);

	public Integer queryCountList(BookRelateCode bookRelateCode);

	public Integer update(BookRelateCode bookRelateCode);
}
