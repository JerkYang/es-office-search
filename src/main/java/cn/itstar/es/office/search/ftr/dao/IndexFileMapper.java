package cn.itstar.es.office.search.ftr.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.ftr.entity.IndexFileEntity;

/**
 * 索引文件 dao
 * @author star
 *
 */
@MapperScan
public interface IndexFileMapper extends BaseMapper<IndexFileEntity> {
	
}
