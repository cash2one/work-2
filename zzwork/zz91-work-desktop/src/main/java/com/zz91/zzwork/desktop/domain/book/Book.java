/**
 * Project name: zz91-mail
 * File name: Account.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.zzwork.desktop.domain.book;

import java.util.Date;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-16
 */
public class Book {
	private Integer id; // INT NOT NULL AUTO_INCREMENT ,
	private String name;// VARCHAR(200) NULL COMMENT '书名' ,
	private String author; // VARCHAR(200) NULL COMMENT '作者' ,
	private String type; // VARCHAR(200) NULL COMMENT '图书类别\n有 图书、月刊、期刊等' ,
	private String press; // VARCHAR(200) NULL COMMENT '出版社' ,
	private String donatePerson; // VARCHAR(200) NULL COMMENT '捐赠人' ,
	private String donateDepart; // VARCHAR(200) NULL COMMENT '捐赠部门' ,
	private Date donateTime; // DATETIME NULL ,
	private Date gmtCreated; // DATETIME NULL ,
	private Date gmtModified; // DATETIME NULL ,
	private String picCover;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getDonatePerson() {
		return donatePerson;
	}

	public void setDonatePerson(String donatePerson) {
		this.donatePerson = donatePerson;
	}

	public String getDonateDepart() {
		return donateDepart;
	}

	public void setDonateDepart(String donateDepart) {
		this.donateDepart = donateDepart;
	}

	public Date getDonateTime() {
		return donateTime;
	}

	public void setDonateTime(Date donateTime) {
		this.donateTime = donateTime;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getPicCover() {
		return picCover;
	}

	public void setPicCover(String picCover) {
		this.picCover = picCover;
	}

}
