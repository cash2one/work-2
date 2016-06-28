/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-5
 */
package com.zz91.zzwork.desktop.service.staff;

import java.util.List;

import com.zz91.zzwork.desktop.domain.staff.Dept;
import com.zz91.zzwork.desktop.dto.ExtTreeDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-5
 */
public interface DeptService {

	public List<ExtTreeDto> queryDeptNode(String parentCode);
	
	public List<ExtTreeDto> queryDeptRightNode(String parentCode, Integer deptId);
	
	public Integer updateDeptRight(Integer deptId, Integer rightId, Boolean checked);
	
	public Integer deleteDept(String code);
	
	public Integer createDept(Dept dept, String parentCode);
	
	public Integer updateDept(Dept dept);
	
	public Dept queryOneDept(String code);
}
