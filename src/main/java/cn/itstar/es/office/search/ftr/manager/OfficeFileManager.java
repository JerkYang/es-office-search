package cn.itstar.es.office.search.ftr.manager;

import java.util.List;
import java.util.Set;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;

/**
 * 文件管理
 */
public interface OfficeFileManager {

	OfficeFileEntity getByOfficeFileName(String officeFile);
	
	List<OfficeFileEntity> listOfficeFile(Page<OfficeFileEntity> page, Query search);
	
	/**
	 * 根据文件级别分页查询
	 * @param page
	 * @param search
	 * @return
	 */
	List<OfficeFileEntity> listOfficeFileByFileLevel(Page<OfficeFileEntity> page, Query search);
	
	int saveOfficeFile(OfficeFileEntity officeFile);
	
	OfficeFileEntity getById(Long officeFileId);
	
	int updateOfficeFile(OfficeFileEntity officeFile);
	
	int batchRemove(Long[] id);
	
	Set<String> listOfficeFilePerms(Long officeFileId);
	
	int updateOfficeFileEnable(Long[] id);
	
	int updateOfficeFileDisable(Long[] id);
	
	OfficeFileEntity getOfficeFileById(Long officeFileId);

	List<OfficeFileEntity> listOfficeFileByGmtDayModified();
}
