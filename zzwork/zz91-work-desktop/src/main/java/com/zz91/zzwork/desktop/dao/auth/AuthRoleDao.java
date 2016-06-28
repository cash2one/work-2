package com.zz91.zzwork.desktop.dao.auth;

import java.util.List;

import com.zz91.zzwork.desktop.domain.auth.AuthRole;

public interface AuthRoleDao {

	public List<AuthRole> queryRole();

	public Integer insertRole(AuthRole role);

	public Integer deleteRole(Integer roleId);

	public Integer deleteRightOfRole(Integer roleId);

	public Integer insertRoleRight(Integer roleId, Integer rightId);

	public Integer deleteRoleRight(Integer roleId, Integer rightId);

	public List<Integer> queryRightIdOfRole(Integer roleId);

	public Integer updateRole(AuthRole role);
}
