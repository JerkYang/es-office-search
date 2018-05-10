package cn.itstar.es.office.search.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.itstar.es.office.search.common.dao.SysLogMapper;
import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.common.entity.SysLogEntity;
import cn.itstar.es.office.search.common.manager.SysLogManager;

/**
 * 系统日志
 */
@Component("sysLogManager")
public class SysLogManagerImpl implements SysLogManager {

	@Autowired
	private SysLogMapper sysLogMapper;
	
	@Override
	public void saveLog(SysLogEntity log) {
		sysLogMapper.save(log);
	}

	@Override
	public List<SysLogEntity> listLog(Page<SysLogEntity> page, Query query) {
		return sysLogMapper.listForPage(page, query);
	}

	@Override
	public int batchRemove(Long[] id) {
		return sysLogMapper.batchRemove(id);
	}

	@Override
	public int batchRemoveAll() {
		return sysLogMapper.batchRemoveAll();
	}

}
