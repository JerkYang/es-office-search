package cn.itstar.es.office.search.ftr.manager;

import java.util.List;
import java.util.Set;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.ftr.entity.IKWordEntity;

/**
 * 索引拓展词管理
 */
public interface IKWordManager {

	IKWordEntity getByIKWordName(String iKWord);
	
	List<IKWordEntity> listIKWord(Page<IKWordEntity> page, Query search);
	
	int saveIKWord(IKWordEntity iKWord);
	
	IKWordEntity getById(Long iKWordId);
	
	int updateIKWord(IKWordEntity iKWord);
	
	int batchRemove(Long[] id);
	
	Set<String> listIKWordPerms(Long iKWordId);
	
	int updateIKWordEnable(Long[] id);
	
	int updateIKWordDisable(Long[] id);
	
	IKWordEntity getIKWordById(Long iKWordId);
}
