package cn.itstar.es.office.search.ftr.manager;

import java.util.List;
import java.util.Set;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.ftr.entity.IKStopEntity;

/**
 * 索引停用词管理
 */
public interface IKStopManager {

	IKStopEntity getByIKStopName(String iKStop);
	
	List<IKStopEntity> listIKStop(Page<IKStopEntity> page, Query search);
	
	int saveIKStop(IKStopEntity iKStop);
	
	IKStopEntity getById(Long iKStopId);
	
	int updateIKStop(IKStopEntity iKStop);
	
	int batchRemove(Long[] id);
	
	Set<String> listIKStopPerms(Long iKStopId);
	
	int updateIKStopEnable(Long[] id);
	
	int updateIKStopDisable(Long[] id);
	
	IKStopEntity getIKStopById(Long iKStopId);
}
