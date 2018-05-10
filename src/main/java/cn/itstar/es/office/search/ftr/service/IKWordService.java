package cn.itstar.es.office.search.ftr.service;

import java.util.Map;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.ftr.entity.IKWordEntity;

public interface IKWordService {
	
	Page<IKWordEntity> listIKWord(Map<String, Object> params);
	
	R saveIKWord(IKWordEntity iKWord);
	
	R getIKWordById(Long iKWordId);
	
	R updateIKWord(IKWordEntity iKWord);
	
	R batchRemove(Long[] id);
	
	R listIKWordPerms(Long iKWordId);
	
	R updateIKWordEnable(Long[] id);
	
	R updateIKWordDisable(Long[] id);

}
