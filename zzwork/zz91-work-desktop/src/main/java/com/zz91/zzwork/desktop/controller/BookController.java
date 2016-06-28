package com.zz91.zzwork.desktop.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.zzwork.desktop.domain.book.Book;
import com.zz91.zzwork.desktop.domain.book.BookDto;
import com.zz91.zzwork.desktop.dto.ExtResult;
import com.zz91.zzwork.desktop.dto.PageDto;
import com.zz91.zzwork.desktop.service.book.BookService;

@Controller
public class BookController extends BaseController {

	@Resource
	private BookService bookService;

	@RequestMapping
	public ModelAndView index() {
		return null;
	}

	@RequestMapping
	public ModelAndView query(Map<String, Object> out, PageDto<BookDto> page,
			Book book) {
		page = bookService.pageBook(book, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView queryOne(Map<String, Object> out, Integer id) {
		PageDto<BookDto> page = new PageDto<BookDto>();
		List<BookDto> list = new ArrayList<BookDto>();
		BookDto dto = bookService.queryOne(id);
		list.add(dto);
		page.setRecords(list);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView doAddOrUpdate(Map<String, Object> out, Book book,
			String donateTimeStr, String code) throws ParseException {
		ExtResult result = new ExtResult();
		if (StringUtils.isNotEmpty(donateTimeStr)) {
			book.setDonateTime(DateUtil.getDate(donateTimeStr, "yyyy-MM-dd"));
		}
		Integer i = 0;
		if (book.getId() != null) {
			i = bookService.update(book);
		} else {
			i = bookService.insert(book, code);
		}
		if (0 < i) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView search(Map<String, Object> out,
			HttpServletRequest request, Book book, PageDto<BookDto> page,
			String keywords) throws UnsupportedEncodingException {
		if (StringUtils.isNotEmpty(keywords)) {
			if (!StringUtils.isContainCNChar(keywords)) {
				keywords = StringUtils.decryptUrlParameter(keywords);
			}
			if (!StringUtils.isContainCNChar(keywords)) {
				keywords = StringUtils.decryptUrlParameter(keywords);
			}
			book.setName(keywords);
			out.put("page", bookService.pageBook(book, page));
		}
		return null;
	}

	@RequestMapping
	public ModelAndView importBook() {
		return null;
	}

	@RequestMapping
	public ModelAndView doImportBook(HttpServletRequest request) throws ParseException {
		MultipartRequest multipartRequest = (MultipartRequest) request;

		MultipartFile file = multipartRequest.getFile("excel");

		try {
			InputStream in = new BufferedInputStream(file.getInputStream());

			HSSFWorkbook wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0);

			int f = sheet.getFirstRowNum();
			int l = sheet.getLastRowNum();
			in.close();
			HSSFRow row;
			for (int i = f + 1; i <= l; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				//name	author	type	press	donate_person	donate_depart	code	donate_time
				Book book =new Book();
				book.setName(row.getCell(1).getRichStringCellValue().toString());
				book.setAuthor(row.getCell(2).getRichStringCellValue().toString());
				book.setType(row.getCell(3).getRichStringCellValue().toString());
				book.setPress(row.getCell(4).getRichStringCellValue().toString());
				book.setDonatePerson(row.getCell(5).getRichStringCellValue().toString());
				book.setDonateDepart(row.getCell(6).getRichStringCellValue().toString());
				Date date = new Date();
				try {
					date = row.getCell(8).getDateCellValue();
					if(date==null){
						date = new Date();
					}
				} catch (Exception e) {
					date = new Date();
				}
				book.setDonateTime(date);
				bookService.insert(book, row.getCell(7).getRichStringCellValue().toString());
			}
		} catch (IOException e) {
		}
		return null;

	}
}
