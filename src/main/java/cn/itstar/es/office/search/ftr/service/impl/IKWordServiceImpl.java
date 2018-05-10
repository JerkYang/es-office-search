package cn.itstar.es.office.search.ftr.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.utils.CommonUtils;
import cn.itstar.es.office.search.ftr.entity.IKWordEntity;
import cn.itstar.es.office.search.ftr.manager.IKWordManager;
import cn.itstar.es.office.search.ftr.service.IKWordService;

@Service("ikWordService")
public class IKWordServiceImpl implements IKWordService {

	@Autowired
	private IKWordManager ikWordManager;

	@Override
	public Page<IKWordEntity> listIKWord(Map<String, Object> params) {
		Query form = new Query(params);
		Page<IKWordEntity> page = new Page<>(form);
		ikWordManager.listIKWord(page, form);
		return page;
	}

	@Override
	public R saveIKWord(IKWordEntity ikWord) {
		int count = ikWordManager.saveIKWord(ikWord);
		return CommonUtils.msg(count);
	}

	@Override
	public R getIKWordById(Long ikWordId) {
		IKWordEntity ikWord = ikWordManager.getById(ikWordId);
		return CommonUtils.msg(ikWord);
	}

	@Override
	public R updateIKWord(IKWordEntity ikWord) {
		int count = ikWordManager.updateIKWord(ikWord);
		return CommonUtils.msg(count);
	}

	@Override
	public R batchRemove(Long[] id) {
		int count = ikWordManager.batchRemove(id);
		return CommonUtils.msg(count);
	}

	@Override
	public R listIKWordPerms(Long ikWordId) {
		Set<String> perms = ikWordManager.listIKWordPerms(ikWordId);
		return CommonUtils.msgNotCheckNull(perms);
	}

	@Override
	public R updateIKWordEnable(Long[] id) {
		int count = ikWordManager.updateIKWordEnable(id);
		return CommonUtils.msg(id, count);
	}

	@Override
	public R updateIKWordDisable(Long[] id) {
		int count = ikWordManager.updateIKWordDisable(id);
		return CommonUtils.msg(id, count);
	}

}
