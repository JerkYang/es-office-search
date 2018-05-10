package cn.itstar.es.office.search.shiro.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.shiro.dao.SysRoleDataPrivilegeMapper;
import cn.itstar.es.office.search.shiro.dao.SysRoleMapper;
import cn.itstar.es.office.search.shiro.dao.SysRoleMenuMapper;
import cn.itstar.es.office.search.shiro.dao.SysRoleOrgMapper;
import cn.itstar.es.office.search.shiro.dao.SysUserRoleMapper;
import cn.itstar.es.office.search.shiro.entity.SysRoleEntity;
import cn.itstar.es.office.search.shiro.manager.SysRoleManager;

/**
 * 系统角色
 */
@Component("sysRoleManager")
public class SysRoleManagerImpl implements SysRoleManager {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	
	@Autowired
	private SysRoleOrgMapper sysRoleOrgMapper;
	
	@Autowired
	private SysRoleDataPrivilegeMapper sysRoleDataPrivilegeMapper;

	@Override
	public List<SysRoleEntity> listRole(Page<SysRoleEntity> page, Query search) {
		return sysRoleMapper.listForPage(page, search);
	}

	@Override
	public int saveRole(SysRoleEntity role) {
		return sysRoleMapper.save(role);
	}

	/**
	 * 根据角色 ID 查询出所有与之有关的数据
	 */
	@Override
	public SysRoleEntity getRoleById(Long id) {
		SysRoleEntity role = sysRoleMapper.getObjectById(id);
		List<Long> menuId = sysRoleMenuMapper.listMenuId(id);
		List<Long> dataPrivilegeId = sysRoleDataPrivilegeMapper.listDataPrivilegeId(id);
		List<Long> orgId = sysRoleOrgMapper.listOrgId(id);
		role.setMenuIdList(menuId);
		role.setDataPrivilegeIdList(dataPrivilegeId);
		role.setOrgIdList(orgId);
		return role;
	}

	@Override
	public int updateRole(SysRoleEntity role) {
		return sysRoleMapper.update(role);
	}

	@Override
	public int batchRemove(Long[] id) {
		int count = sysRoleMapper.batchRemove(id);
		sysUserRoleMapper.batchRemoveByRoleId(id);
		sysRoleMenuMapper.batchRemoveByRoleId(id);
		sysRoleDataPrivilegeMapper.batchRemoveByRoleId(id);
		sysRoleOrgMapper.batchRemoveByRoleId(id);
		return count;
	}

	@Override
	public List<SysRoleEntity> listRole() {
		return sysRoleMapper.list();
	}

	@Override
	public int updateRoleOptAuthorization(SysRoleEntity role) {
		Long roleId = role.getRoleId();
		int count = sysRoleMenuMapper.remove(roleId);
		Query query = new Query();
		query.put("roleId", roleId);
		List<Long> menuId = role.getMenuIdList();
		if(menuId.size() > 0) {
			query.put("menuIdList", role.getMenuIdList());
			count = sysRoleMenuMapper.save(query);
		}
		return count;
	}
	
	

	/**
	 * 数据操作权限
	 */
	@Override
	public int updateRoleDataAuthorization(SysRoleEntity role) {
		Long roleId = role.getRoleId();
		int count = sysRoleDataPrivilegeMapper.remove(roleId);
		// int count = sysRoleOrgMapper.remove(roleId);
		Query query = new Query();
		query.put("roleId", roleId);
		List<Long> dataId = role.getDataPrivilegeIdList();
		if(dataId.size() > 0) {
			query.put("dataPrivilegeIdList", role.getDataPrivilegeIdList());
			count = sysRoleDataPrivilegeMapper.save(query);
			// count = sysRoleOrgMapper.save(query);
		}
		return count;
	}
	
}
