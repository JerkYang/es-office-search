package cn.itstar.es.office.search.ftr.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.ftr.entity.IKStopEntity;

/**
 * 系统索引停用词dao
 * @author star
 *
 */
@MapperScan
public interface IKStopMapper extends BaseMapper<IKStopEntity> {

	IKStopEntity getByIKStopName(String ikStopName);
	
	int updateIKStopStatus(Query query);

	List<IKStopEntity> listForPage();
}
