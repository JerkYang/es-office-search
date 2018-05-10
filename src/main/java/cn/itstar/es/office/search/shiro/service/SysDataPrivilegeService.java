package cn.itstar.es.office.search.shiro.service;

import java.util.List;

import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;

/**
 * 数据权限
 */
public interface SysDataPrivilegeService {

	List<SysDataPrivilegeEntity> listDataPrivilege();
	
	List<SysDataPrivilegeEntity> listDataPrivilegeTree();
	
	R saveDataPrivilege(SysDataPrivilegeEntity dataPrivilege);
	
	R getDataPrivilege(Long DataPrivilegeId);
	
	R updateDataPrivilege(SysDataPrivilegeEntity dataPrivilege);
	
	R bactchRemoveDataPrivilege(Long[] id);
	
}
