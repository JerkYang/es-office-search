package cn.itstar.es.office.search.ftr.manager.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.itstar.es.office.search.common.constant.SystemConstant.StatusType;
import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.ftr.dao.IKStopMapper;
import cn.itstar.es.office.search.ftr.entity.IKStopEntity;
import cn.itstar.es.office.search.ftr.manager.IKStopManager;

/**
 * 系统文件
 */
@Component("ikStopManager")
public class IKStopManagerImpl implements IKStopManager {

	@Autowired
	private IKStopMapper ikStopMapper;

	@Override
	public IKStopEntity getByIKStopName(String ikStop) {
		return ikStopMapper.getByIKStopName(ikStop);
	}

	@Override
	public List<IKStopEntity> listIKStop(Page<IKStopEntity> page, Query search) {
		return ikStopMapper.listForPage(page, search);
	}

	@Override
	public int saveIKStop(IKStopEntity ikStop) {
		return ikStopMapper.save(ikStop);
	}

	@Override
	public IKStopEntity getById(Long ikStopId) {
		return ikStopMapper.getObjectById(ikStopId);
	}

	@Override
	public int updateIKStop(IKStopEntity ikStop) {
		return ikStopMapper.update(ikStop);
	}

	@Override
	public int batchRemove(Long[] id) {
		return ikStopMapper.batchRemove(id);
	}

	@Override
	public Set<String> listIKStopPerms(Long ikStopId) {
		/*List<String> perms = ikStopMapper.listIKStopPerms(iKStopId);
		Set<String> permsSet = new HashSet<>();
		for(String perm : perms) {
			if(StringUtils.isNotBlank(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;*/
		return null;
	}

	@Override
	public int updateIKStopEnable(Long[] id) {
		Query query = new Query();
		query.put("status", StatusType.ENABLE.getValue());
		query.put("id", id);
		int count = ikStopMapper.updateIKStopStatus(query);
		return count;
	}

	@Override
	public int updateIKStopDisable(Long[] id) {
		Query query = new Query();
		query.put("status", StatusType.DISABLE.getValue());
		query.put("id", id);
		int count = ikStopMapper.updateIKStopStatus(query);
		return count;
	}

	@Override
	public IKStopEntity getIKStopById(Long IKStopId) {
		return ikStopMapper.getObjectById(IKStopId);
	}

}
