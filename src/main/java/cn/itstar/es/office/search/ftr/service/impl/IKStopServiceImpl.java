package cn.itstar.es.office.search.ftr.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.utils.CommonUtils;
import cn.itstar.es.office.search.ftr.entity.IKStopEntity;
import cn.itstar.es.office.search.ftr.manager.IKStopManager;
import cn.itstar.es.office.search.ftr.service.IKStopService;

@Service("ikStopService")
public class IKStopServiceImpl implements IKStopService {

	@Autowired
	private IKStopManager ikStopManager;

	@Override
	public Page<IKStopEntity> listIKStop(Map<String, Object> params) {
		Query form = new Query(params);
		Page<IKStopEntity> page = new Page<>(form);
		ikStopManager.listIKStop(page, form);
		return page;
	}

	@Override
	public R saveIKStop(IKStopEntity ikStop) {
		int count = ikStopManager.saveIKStop(ikStop);
		return CommonUtils.msg(count);
	}

	@Override
	public R getIKStopById(Long ikStopId) {
		IKStopEntity ikStop = ikStopManager.getById(ikStopId);
		return CommonUtils.msg(ikStop);
	}

	@Override
	public R updateIKStop(IKStopEntity ikStop) {
		int count = ikStopManager.updateIKStop(ikStop);
		return CommonUtils.msg(count);
	}

	@Override
	public R batchRemove(Long[] id) {
		int count = ikStopManager.batchRemove(id);
		return CommonUtils.msg(count);
	}

	@Override
	public R listIKStopPerms(Long ikStopId) {
		Set<String> perms = ikStopManager.listIKStopPerms(ikStopId);
		return CommonUtils.msgNotCheckNull(perms);
	}

	@Override
	public R updateIKStopEnable(Long[] id) {
		int count = ikStopManager.updateIKStopEnable(id);
		return CommonUtils.msg(id, count);
	}

	@Override
	public R updateIKStopDisable(Long[] id) {
		int count = ikStopManager.updateIKStopDisable(id);
		return CommonUtils.msg(id, count);
	}
	
}
