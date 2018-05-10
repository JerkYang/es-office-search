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
import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.ftr.dao.IKWordMapper;
import cn.itstar.es.office.search.ftr.entity.IKWordEntity;
import cn.itstar.es.office.search.ftr.manager.IKWordManager;

/**
 * 系统文件
 */
@Component("ikWordManager")
public class IKWordManagerImpl implements IKWordManager {

	@Autowired
	private IKWordMapper IKWordMapper;

	@Override
	public IKWordEntity getByIKWordName(String ikWord) {
		return IKWordMapper.getByIKWordName(ikWord);
	}

	@Override
	public List<IKWordEntity> listIKWord(Page<IKWordEntity> page, Query search) {
		return IKWordMapper.listForPage(page, search);
	}

	@Override
	public int saveIKWord(IKWordEntity ikWord) {
		return IKWordMapper.save(ikWord);
	}

	@Override
	public IKWordEntity getById(Long ikWordId) {
		return IKWordMapper.getObjectById(ikWordId);
	}

	@Override
	public int updateIKWord(IKWordEntity ikWord) {
		return IKWordMapper.update(ikWord);
	}

	@Override
	public int batchRemove(Long[] id) {
		return IKWordMapper.batchRemove(id);
	}

	@Override
	public Set<String> listIKWordPerms(Long ikWordId) {
		/*List<String> perms = IKWordMapper.listUserPerms(userId);
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
	public int updateIKWordEnable(Long[] id) {
		Query query = new Query();
		query.put("status", StatusType.ENABLE.getValue());
		query.put("id", id);
		int count = IKWordMapper.updateIKWordStatus(query);
		return count;
	}

	@Override
	public int updateIKWordDisable(Long[] id) {
		Query query = new Query();
		query.put("status", StatusType.DISABLE.getValue());
		query.put("id", id);
		int count = IKWordMapper.updateIKWordStatus(query);
		return count;
	}

	@Override
	public IKWordEntity getIKWordById(Long ikWordId) {
		return IKWordMapper.getObjectById(ikWordId);
	}

}
