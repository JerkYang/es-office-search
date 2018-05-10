package cn.itstar.es.office.search.ftr.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.ftr.entity.IKWordEntity;

/**
 * 系统拓展词dao
 * @author star
 *
 */
@MapperScan
public interface IKWordMapper extends BaseMapper<IKWordEntity> {

	IKWordEntity getByIKWordName(String ikWordName);
	
	int updateIKWordStatus(Query query);

	List<IKWordEntity> listForPage();
}
