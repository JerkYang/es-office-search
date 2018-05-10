package cn.itstar.es.office.search.common.dao;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.entity.SysLogEntity;

/**
 * 系统日志 
 */
@MapperScan
public interface SysLogMapper extends BaseMapper<SysLogEntity> {

	int batchRemoveAll();
	
}
