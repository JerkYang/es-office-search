package cn.itstar.es.office.search.ftr.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.ftr.entity.IKStopEntity;

public interface IKStopService {
	
	Page<IKStopEntity> listIKStop(Map<String, Object> params);
	
	R saveIKStop(IKStopEntity iKStop);
	
	R getIKStopById(Long iKStopId);
	
	R updateIKStop(IKStopEntity iKStop);
	
	R batchRemove(Long[] id);
	
	R listIKStopPerms(Long ikStopId);
	
	R updateIKStopEnable(Long[] id);
	
	R updateIKStopDisable(Long[] id);
	
}
