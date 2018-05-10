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

public interface IndexFileService {
	
	Page<OfficeFileEntity> listIndexFile(Map<String, Object> params);
	
	R saveIndexFile(MultipartFile[] uploadFile, OfficeFileEntity officeFile);
	
	R getIndexFileById(Long indexFileId);
	
	R updateIndexFile(OfficeFileEntity indexFile);
	
	R batchRemove(Long[] id);
	
	R listIndexFilePerms(Long userId);
	
	R updateIndexFileEnable(Long[] id);
	
	R updateIndexFileDisable(Long[] id);

	/**
	 * 查询出当前用户所拥有的所有文件级别
	 */
	List<SysDataPrivilegeEntity> listLevelTree(Long userId);

	R addDBIndexFile();

	R downloadOfficeFile(Long indexFileId);

	Page<OfficeFileEntity> searchIndex(Map<String, Object> params);

	//Page<OfficeFileEntity> addAllIndex(Map<String, Object> params);

	
}
