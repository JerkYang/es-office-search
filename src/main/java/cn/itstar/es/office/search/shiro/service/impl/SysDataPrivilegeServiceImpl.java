package cn.itstar.es.office.search.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itstar.es.office.search.common.constant.MsgConstant;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.utils.CommonUtils;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;
import cn.itstar.es.office.search.shiro.manager.SysDataPrivilegeManager;
import cn.itstar.es.office.search.shiro.service.SysDataPrivilegeService;

/**
 * 数据权限
 */
@Service("sysDataPrivilegeService")
public class SysDataPrivilegeServiceImpl implements SysDataPrivilegeService {

	@Autowired
	private SysDataPrivilegeManager sysDataPrivilegeManager;
	
	@Override
	public List<SysDataPrivilegeEntity> listDataPrivilege() {
		return sysDataPrivilegeManager.listDataPrivilege();
	}

	@Override
	public List<SysDataPrivilegeEntity> listDataPrivilegeTree() {
		List<SysDataPrivilegeEntity> dataPrivilegeList = sysDataPrivilegeManager.listDataPrivilege();
		SysDataPrivilegeEntity dataPrivilege = new SysDataPrivilegeEntity();
		dataPrivilege.setDataId(0L);
		dataPrivilege.setName("一级数据");
		dataPrivilege.setParentId(-1L);
		dataPrivilege.setOpen(true);
		dataPrivilegeList.add(dataPrivilege);
		return dataPrivilegeList;
	}

	@Override
	public R saveDataPrivilege(SysDataPrivilegeEntity dataPrivilege) {
		int count = sysDataPrivilegeManager.saveDataPrivilege(dataPrivilege);
		return CommonUtils.msg(count);
	}

	@Override
	public R getDataPrivilege(Long dataPrivilegeId) {
		SysDataPrivilegeEntity dataPrivilege = sysDataPrivilegeManager.getDataPrivilege(dataPrivilegeId);
		return CommonUtils.msg(dataPrivilege);
	}

	@Override
	public R updateDataPrivilege(SysDataPrivilegeEntity dataPrivilege) {
		int count = sysDataPrivilegeManager.updateDataPrivilege(dataPrivilege);
		return CommonUtils.msg(count);
	}

	@Override
	public R bactchRemoveDataPrivilege(Long[] id) {
		boolean children = sysDataPrivilegeManager.hasChildren(id);
		if(children) {
			return R.error(MsgConstant.MSG_HAS_CHILD);
		}
		int count = sysDataPrivilegeManager.bactchRemoveDataPrivilege(id);
		return CommonUtils.msg(id, count);
	}

}
