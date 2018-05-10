package cn.itstar.es.office.search.quartz.service;

import java.util.Map;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.quartz.entity.QuartzJobLogEntity;

/**
 * 定时任务日志
 *
 */
public interface QuartzJobLogService {

	Page<QuartzJobLogEntity> listForPage(Map<String, Object> params);
	
	R batchRemove(Long[] id);
	
	R batchRemoveAll();
	
}
