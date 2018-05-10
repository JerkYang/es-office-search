package cn.itstar.es.office.search.ftr.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;

public interface OfficeFileService {
	
	Page<OfficeFileEntity> listOfficeFile(Map<String, Object> params);
	
	R saveOfficeFile(MultipartFile[] uploadFile, OfficeFileEntity officeFile);
	
	R getOfficeFileById(Long officeFileId);
	
	R updateOfficeFile(OfficeFileEntity officeFile);
	
	R batchRemove(Long[] id);
	
	R listOfficeFilePerms(Long userId);
	
	R updateOfficeFileEnable(Long[] id);
	
	R updateOfficeFileDisable(Long[] id);

	/**
	 * 查询出当前用户所拥有的所有文件级别
	 */
	List<SysDataPrivilegeEntity> listLevelTree(Long userId);

	R downloadOfficeFile(Long[] ids);

	/**
	 * 根据文件修改日期查询出当前修改过的所有文件信息
	 * @return
	 */
	List<OfficeFileEntity> listOfficeFileByGmtDayModified();

	R downLoad(HttpServletRequest req, HttpServletResponse resp, OfficeFileEntity officeFile);

	R downloadOneOfficeFile(Long id);

	
}
