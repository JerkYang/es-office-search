package cn.itstar.es.office.search.ftr.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.ftr.entity.SysRoleFileLeveEntity;

/**
 * 角色与文件的关系
 */
@MapperScan
public interface SysRoleFileLeveMapper extends BaseMapper<SysRoleFileLeveEntity> {

	/**
	 * 根据角色 ID 查询出所有与之相关的文件级别
	 * @param roleId
	 * @return
	 */
	List<Long> listFileLevelByRoleId(Long roleId);
	
	int batchRemoveByFileLevelId(Long[] id);
	
	int batchRemoveByRoleId(Long[] id);
	
}
