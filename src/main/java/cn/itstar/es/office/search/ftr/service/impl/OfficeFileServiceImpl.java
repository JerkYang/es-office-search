package cn.itstar.es.office.search.ftr.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.itstar.es.office.search.common.constant.SystemConstant;
import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.common.utils.CommonUtils;
import cn.itstar.es.office.search.common.utils.ElasticSearchUtils;
import cn.itstar.es.office.search.common.utils.HttpContextUtils;
import cn.itstar.es.office.search.ftr.dao.SysRoleFileLeveMapper;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.ftr.manager.OfficeFileManager;
import cn.itstar.es.office.search.ftr.service.OfficeFileService;
import cn.itstar.es.office.search.ftr.utils.ESUtil;
import cn.itstar.es.office.search.ftr.utils.FileUtil;
import cn.itstar.es.office.search.ftr.utils.POIUtil;
import cn.itstar.es.office.search.ftr.utils.PreSubFixUtil;
import cn.itstar.es.office.search.ftr.utils.UpAndDownLoadUtil;
import cn.itstar.es.office.search.shiro.dao.SysRoleDataPrivilegeMapper;
import cn.itstar.es.office.search.shiro.dao.SysUserRoleMapper;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;
import cn.itstar.es.office.search.shiro.manager.SysDataPrivilegeManager;
import cn.itstar.es.office.search.shiro.manager.SysUserManager;

@Service("officeFileService")
public class OfficeFileServiceImpl implements OfficeFileService {

	@Autowired
	private OfficeFileManager officeFileManager;
	@Autowired
	private SysUserManager sysUserManager;
	
	@Autowired
	private SysRoleDataPrivilegeMapper sysRoleDataPrivilegeMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysRoleFileLeveMapper sysRoleFileMapper;
	
	@Autowired
	private SysDataPrivilegeManager sysDataPrivilegeManager;

	/**
	 * 根据文件级别分页查询所有文件
	 */
	@Override
	public Page<OfficeFileEntity> listOfficeFile(Map<String, Object> params) {
		Long userId = (Long) params.get("userId");
		Query form = new Query(params);
		Page<OfficeFileEntity> page = new Page<>(form);
		// officeFileManager.listOfficeFile(page, form);
		List<Long> roleIds = sysUserRoleMapper.listUserRoleId(userId);
		List<Long> fileLevelIds = null;
		List<Long> ids = new ArrayList<Long>();
		for (Long roleId : roleIds) {
			fileLevelIds = sysRoleDataPrivilegeMapper.listDataPrivilegeId(roleId);
			for (Long fileLevelId : fileLevelIds) {
				if(!(ids.contains(fileLevelId))) {
					ids.add(fileLevelId);
				}
			}
		}
		form.put("ids", ids);
		officeFileManager.listOfficeFileByFileLevel(page, form);
		return page;
	}
	
	/**
	 * 根据当前用户查询出该用户所拥有的所有文件级别ID
	 */
	@Override
	public List<SysDataPrivilegeEntity> listLevelTree(Long userId) {
		
		/*List<SysDataPrivilegeEntity> dataPrivilegeEntitys = sysDataPrivilegeManager.listDataPrivilege();
		List<SysDataPrivilegeEntity> dataPrivilegeList = sysDataPrivilegeManager.listDataPrivilegeByUserId(userId);
		SysDataPrivilegeEntity dataPrivilege = new SysDataPrivilegeEntity();
		dataPrivilege.setDataId(0L);
		dataPrivilege.setName("文件级别");
		dataPrivilege.setParentId(-1L);
		dataPrivilege.setOpen(true);
		dataPrivilegeList.add(dataPrivilege);*/
		
		List<SysDataPrivilegeEntity> dataPrivilegeEntitys = sysDataPrivilegeManager.listDataPrivilegeByUserId(userId);
		/*SysDataPrivilegeEntity dataPrivilegeEntity = new SysDataPrivilegeEntity();
		dataPrivilegeEntity.setDataId(0L);
		dataPrivilegeEntity.setName("文件级别");
		dataPrivilegeEntity.setOpen(true);
		dataPrivilegeEntity.setParentId(-1L);
		dataPrivilegeEntitys.add(dataPrivilegeEntity);*/
		return dataPrivilegeEntitys;
		/*List<Long> roleIds = sysUserRoleMapper.listUserRoleId(userId);
		List<Long> fileLevelIds = null;
		List<Long> ids = new ArrayList<Long>();
		SysDataPrivilegeEntity dataPrivilegeEntity = new SysDataPrivilegeEntity();
		List<SysDataPrivilegeEntity> dataPrivilegeEntitys = new ArrayList<SysDataPrivilegeEntity>();
		
		dataPrivilegeEntity.setDataId(0L);
		dataPrivilegeEntity.setName("文件级别");
		dataPrivilegeEntity.setOpen(true);
		dataPrivilegeEntity.setParentId(-1L);
		dataPrivilegeEntitys.add(dataPrivilegeEntity);
		
		for (Long roleId : roleIds) {
			fileLevelIds = sysRoleDataPrivilegeMapper.listDataPrivilegeId(roleId);
			for (Long fileLevelId : fileLevelIds) {
				if(!(ids.contains(fileLevelId))) {
					dataPrivilegeEntity = sysDataPrivilegeManager.getDataPrivilege(fileLevelId);
					dataPrivilegeEntitys.add(dataPrivilegeEntity);
					ids.add(fileLevelId);
				}
			}
		}
		
		return dataPrivilegeEntitys;*/
	}

	/**
	 * 上传并保存文件
	 * @throws IOException 
	 */
	@Override
	public R saveOfficeFile(MultipartFile[] uploadFile, OfficeFileEntity officeFile) {
		HttpServletRequest req = HttpContextUtils.getHttpServletRequest();
		
		String prePath = req.getSession().getServletContext().getRealPath("\\") + "\\" + "upload" + "\\";
 		String timePath = FileUtil.createTimeFiles(prePath);
		for (MultipartFile multipartFile : uploadFile) {
			String filePath = timePath
			// + System.getProperty("file.separator") + 
			+ "\\" + multipartFile.getOriginalFilename();
			InputStream inputStream = null;
			try {
				inputStream = multipartFile.getInputStream();
			} catch (IOException e) {
				return R.error("上传文件内容读取失败");
			}
			// 后缀名
			String sufName = null;
			String clientName = multipartFile.getOriginalFilename();
			// 获取文件后缀名
			sufName = PreSubFixUtil.getFileNameWithSuffix(clientName);
			
			// 上传存储文件
			try {
				
				multipartFile.transferTo(new File(filePath));
			} catch (Exception e) {
				return R.error("上传文件保存失败");
			}
			
			// 文件名
			String fileName = PreSubFixUtil.getFileNameWithoutSuffix(clientName);
			
			// 存到 officeFile中
			SysUserEntity currUser = sysUserManager.getById(officeFile.getUserIdCreate());
			officeFile.setFileName(fileName);
			officeFile.setUserNameCreate(currUser.getUsername());
			officeFile.setUserNameUpdate(currUser.getUsername());
			officeFile.setFilePath(filePath);
			officeFile.setStatus(1);	//0: 禁用; 1:正常
			
			//officeFile.setContentText(officeContent);
			
			int count = 0;
			try {
				count = officeFileManager.saveOfficeFile(officeFile);
			} catch (Exception e) {
				return R.error("保存文件失败，SQL异常");
			}
			/*try {
				ESUtil.addIndex(officeFile, ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), officeFile.getFileId().toString());
			} catch (IOException e) {
				return R.error("创建索引失败！");
			}*/
			return CommonUtils.msg(count);
		}
		return null;
		
	}

	@Override
	public R getOfficeFileById(Long officeFileId) {
		OfficeFileEntity officeFileEntity = officeFileManager.getOfficeFileById(officeFileId);
		return CommonUtils.msg(officeFileEntity);
	}

	@Override
	public R updateOfficeFile(OfficeFileEntity officeFile) {
		int count = officeFileManager.updateOfficeFile(officeFile);
		return CommonUtils.msg(count);
	}

	@Override
	public R batchRemove(Long[] id) {
		for (Long fileId : id) {
			OfficeFileEntity officeFileEntity = officeFileManager.getById(fileId);
			Boolean isExit = FileUtil.deleteFileByPath(officeFileEntity.getFilePath());
			if (!isExit) {
				return R.error("删除文件" + officeFileEntity.getFileName() + "失败！");
			}
		}
		int count = officeFileManager.batchRemove(id);
		return CommonUtils.msg(count);
	}

	@Override
	public R listOfficeFilePerms(Long officeFileId) {
		Set<String> officeFilePerms = officeFileManager.listOfficeFilePerms(officeFileId);
		return CommonUtils.msgNotCheckNull(officeFilePerms);
	}

	@Override
	public R updateOfficeFileEnable(Long[] id) {
		int count = officeFileManager.updateOfficeFileEnable(id);
		return CommonUtils.msg(count);
	}

	@Override
	public R updateOfficeFileDisable(Long[] id) {
		int count = officeFileManager.updateOfficeFileDisable(id);
		return CommonUtils.msg(count);
	}

	/**
	 * 下载文件
	 */
	@Override
	public R downloadOfficeFile(Long[] ids) {
		List<OfficeFileEntity> offices  = new ArrayList<OfficeFileEntity>();
		OfficeFileEntity fileEntity = null;
		//List<Long> idlist = new ArrayList<Long>(); 
		
		for (Long fileId : ids) {
			fileEntity = officeFileManager.getById(fileId);
			offices.add(fileEntity);
			/*if(!(idlist.contains(fileId))) {
				idlist.add(fileId);
			}*/
		}
		if (ids.length == 1) {
			try {
				UpAndDownLoadUtil.DownLoadFile(fileEntity.getFilePath());
				return CommonUtils.msg(1);
				/*if(!count) {
					return R.error("下载失败！");
				}*/
			} catch (Exception e) {
				return CommonUtils.msg(0);
			}
			
		} else {
			
			try {
				UpAndDownLoadUtil.downloadFiles(offices);
				return CommonUtils.msg(1);
			} catch (Exception e) {
				return CommonUtils.msg(0);
			}
		}
		
		
	}

	@Override
	public List<OfficeFileEntity> listOfficeFileByGmtDayModified() {
		return officeFileManager.listOfficeFileByGmtDayModified();
	}

	@Override
	public R downLoad(HttpServletRequest req, HttpServletResponse resp, OfficeFileEntity officeFile) {
		//String id = req.getParameter("id");
		long fileId = officeFile.getFileId();
		officeFile = officeFileManager.getById(fileId);
		String path = officeFile.getFilePath();
		try {
			return UpAndDownLoadUtil.DownLoad(req, resp, path);
		} catch (Exception e) {
			return R.error("下载失败！");
		}
	}

	@Override
	public R downloadOneOfficeFile(Long id) {
		OfficeFileEntity fileEntity = null;
		fileEntity = officeFileManager.getById(id);
		try {
			UpAndDownLoadUtil.DownLoadFile(fileEntity.getFilePath());
			return CommonUtils.msg(1);
		} catch (Exception e) {
			return CommonUtils.msg(0);
		}
	}
}
