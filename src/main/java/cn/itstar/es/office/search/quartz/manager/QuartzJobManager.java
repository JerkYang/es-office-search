package cn.itstar.es.office.search.quartz.manager;

import java.util.List;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.quartz.entity.QuartzJobEntity;

/**
 * 定时任务
 */
public interface QuartzJobManager {

	List<QuartzJobEntity> listForPage(Page<QuartzJobEntity> page, Query query);
	
	List<QuartzJobEntity> listNormalJob();
	
	int saveQuartzJob(QuartzJobEntity job);
	
	QuartzJobEntity getQuartzJobById(Long jobId);
	
	int updateQuartzJob(QuartzJobEntity job);
	
	int batchRemoveQuartzJob(Long[] id);
	
	int batchUpdate(Long[] jobId, Integer status);
	
}
