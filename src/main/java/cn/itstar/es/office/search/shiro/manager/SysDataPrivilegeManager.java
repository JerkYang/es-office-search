package cn.itstar.es.office.search.shiro.manager;

import java.util.List;

import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;


/**
 * 数据权限
 */
public interface SysDataPrivilegeManager {
	
	List<SysDataPrivilegeEntity> listDataPrivilege();
	
	int saveDataPrivilege(SysDataPrivilegeEntity dataPrivilege);
	
	SysDataPrivilegeEntity getDataPrivilege(Long dataPrivilegeId);
	
	int updateDataPrivilege(SysDataPrivilegeEntity dataPrivilege);
	
	int bactchRemoveDataPrivilege(Long[] id);
	
	boolean hasChildren(Long[] id);

	List<Long> listDataPrivilegeChildren(Long parentId);

	List<Long> getAllDataPrivilegeChildren(Long parentId);
	
	/**
	 * 根据用户ID查询文件级别
	 */
	List<SysDataPrivilegeEntity> listDataPrivilegeByUserId(Long userId);
	
}
