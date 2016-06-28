/**
 * 
 */
package com.zz91.zzwork.desktop.dao.staff.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.zz91.zzwork.desktop.dao.staff.ContactsDao;

/**
 * @author root
 *
 */
@Component("contactsDao")
public class ContactsDaoImpl extends SqlMapClientDaoSupport implements ContactsDao {

}
