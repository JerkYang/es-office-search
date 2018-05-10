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
import cn.itstar.es.office.search.ftr.dao.OfficeFileLeveMapper;
import cn.itstar.es.office.search.ftr.dao.OfficeFileMapper;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.ftr.manager.OfficeFileManager;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;

/**
 * 系统文件
 */
@Component("officeFileManager")
public class OfficeFileManagerImpl implements OfficeFileManager {

	@Autowired
	private OfficeFileMapper officeFileMapper;
	
	@Autowired
	private OfficeFileLeveMapper officeFileLevelMapper;

	@Override
	public OfficeFileEntity getByOfficeFileName(String officeFile) {
		return officeFileMapper.getByOfficeFileName(officeFile);
	}

	@Override
	public List<OfficeFileEntity> listOfficeFile(Page<OfficeFileEntity> page, Query search) {
		return officeFileMapper.listForPage(page, search);
	}
	

	@Override
	public List<OfficeFileEntity> listOfficeFileByFileLevel(Page<OfficeFileEntity> page, Query search) {
		return officeFileMapper.listForPageByFileLevel(page, search);
	}

	@Override
	public int saveOfficeFile(OfficeFileEntity officeFile) {
		return officeFileMapper.save(officeFile);
	}

	@Override
	public OfficeFileEntity getById(Long officeFileId) {
		return officeFileMapper.getObjectById(officeFileId);
	}

	@Override
	public int updateOfficeFile(OfficeFileEntity officeFile) {
		return officeFileMapper.update(officeFile);
	}

	@Override
	public int batchRemove(Long[] id) {
		return officeFileMapper.batchRemove(id);
	}

	@Override
	public Set<String> listOfficeFilePerms(Long officeFileId) {
		/*List<String> perms = officeFileMapper.listUserPerms(userId);
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
	public int updateOfficeFileEnable(Long[] id) {
		Query query = new Query();
		query.put("status", StatusType.ENABLE.getValue());
		query.put("id", id);
		int count = officeFileMapper.updateOfficeFileStatus(query);
		return count;
	}

	@Override
	public int updateOfficeFileDisable(Long[] id) {
		Query query = new Query();
		query.put("status", StatusType.DISABLE.getValue());
		query.put("id", id);
		int count = officeFileMapper.updateOfficeFileStatus(query);
		return count;
	}

	@Override
	public OfficeFileEntity getOfficeFileById(Long officeFileId) {
		return officeFileMapper.getObjectById(officeFileId);
	}

	@Override
	public List<OfficeFileEntity> listOfficeFileByGmtDayModified() {
		return officeFileMapper.listOfficeFileByGmtDayModified();
	}

}
