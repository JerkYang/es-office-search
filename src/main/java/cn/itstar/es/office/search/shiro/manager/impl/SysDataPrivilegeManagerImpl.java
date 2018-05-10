package cn.itstar.es.office.search.shiro.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.itstar.es.office.search.common.utils.CommonUtils;
import cn.itstar.es.office.search.shiro.dao.SysDataPrivilegeMapper;
import cn.itstar.es.office.search.shiro.dao.SysRoleDataPrivilegeMapper;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;
import cn.itstar.es.office.search.shiro.manager.SysDataPrivilegeManager;

/**
 * 数据权限
 */
@Component("sysDataPrivilegeManager")
public class SysDataPrivilegeManagerImpl implements SysDataPrivilegeManager {

	@Autowired
	private SysDataPrivilegeMapper sysDataPrivilegeMapper;
	
	@Autowired
	private SysRoleDataPrivilegeMapper sysRoleDataPrivilegeMapper;

	@Override
	public List<SysDataPrivilegeEntity> listDataPrivilege() {
		return sysDataPrivilegeMapper.list();
	}

	@Override
	public int saveDataPrivilege(SysDataPrivilegeEntity dataPrivilege) {
		return sysDataPrivilegeMapper.save(dataPrivilege);
	}

	@Override
	public SysDataPrivilegeEntity getDataPrivilege(Long dataPrivilegeId) {
		return sysDataPrivilegeMapper.getObjectById(dataPrivilegeId);
	}

	@Override
	public int updateDataPrivilege(SysDataPrivilegeEntity dataPrivilege) {
		return sysDataPrivilegeMapper.update(dataPrivilege);
	}

	@Override
	public int bactchRemoveDataPrivilege(Long[] id) {
		int count = sysDataPrivilegeMapper.batchRemove(id);
		sysRoleDataPrivilegeMapper.batchRemoveByDataPrivilegeId(id);
		return count;
	}

	@Override
	public boolean hasChildren(Long[] id) {
		for(Long parentId : id) {
			int count = sysDataPrivilegeMapper.countDataPrivilegeChildren(parentId);
			if(CommonUtils.isIntThanZero(count)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Long> listDataPrivilegeChildren(Long parentId) {
		return sysDataPrivilegeMapper.listDataPrivilegeChildren(parentId);
	}

	@Override
	public List<Long> getAllDataPrivilegeChildren(Long parentId) {
		List<Long> dataPrivilegeIds = new ArrayList<>();
		List<Long> parentIds = listDataPrivilegeChildren(parentId);
		recursionDataPrivilegeChildren(parentIds, dataPrivilegeIds);
		return dataPrivilegeIds;
	}

	/**
	 * 递归查询子数据权限
	 * @param parentIds
	 * @param result
	 */
	public void recursionDataPrivilegeChildren(List<Long> parentIds, List<Long> result) {
		for (Long parentId : parentIds) {
			List<Long> ids = listDataPrivilegeChildren(parentId);
			if (ids.size() > 0) {
				recursionDataPrivilegeChildren(ids, result);
			}
			result.add(parentId);
		}
	}

	/**
	 * 根据用户ID查询文件级别
	 */
	@Override
	public List<SysDataPrivilegeEntity> listDataPrivilegeByUserId(Long userId) {
		return sysRoleDataPrivilegeMapper.listDataPrivilegeByUserId(userId);
	}

}
