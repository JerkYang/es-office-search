package cn.itstar.es.office.search.shiro.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;
import cn.itstar.es.office.search.shiro.entity.SysRoleDataPrivilegeEntity;

/**
 * 角色与数据权限的关系
 */
@MapperScan
public interface SysRoleDataPrivilegeMapper extends BaseMapper<SysRoleDataPrivilegeEntity> {

	/**
	 * 数据权限点开时查询所有树形结构角色所拥有的数据权限
	 * （根据角色 ID 查询出所有的 数据权限 ID）
	 * @param id
	 * @return
	 */
	List<Long> listDataPrivilegeId(Long id);
	
	
	int batchRemoveByDataPrivilegeId(Long[] id);
	
	int batchRemoveByRoleId(Long[] id);
	
	/**
	 * 根据用户ID查询文件级别
	 */
	List<SysDataPrivilegeEntity> listDataPrivilegeByUserId(Long userId);
	
}
