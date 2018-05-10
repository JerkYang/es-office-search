package cn.itstar.es.office.search.shiro.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.shiro.entity.SysRoleMenuEntity;

/**
 * 系统角色与菜单关系
 *
 */
@MapperScan
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {

	int batchRemoveByMenuId(Long[] id);
	
	int batchRemoveByRoleId(Long[] id);
	
	/**
	 * 操作权限点开时查询所有树形结构角色所拥有的菜单
	 * @param id
	 * @return
	 */
	List<Long> listMenuId(Long id);
	
}
