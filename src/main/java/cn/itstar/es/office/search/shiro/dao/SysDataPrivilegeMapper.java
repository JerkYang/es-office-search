package cn.itstar.es.office.search.shiro.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;

/**
 * 数据权限
 *
 */
@MapperScan
public interface SysDataPrivilegeMapper extends BaseMapper<SysDataPrivilegeEntity> {

	int countDataPrivilegeChildren(Long parentId);

	List<Long> listDataPrivilegeChildren(Long parentId);
	
}
