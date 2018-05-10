package cn.itstar.es.office.search.ftr.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.tools.ant.taskdefs.Sleep;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.common.utils.CommonUtils;
import cn.itstar.es.office.search.common.utils.ElasticSearchUtils;
import cn.itstar.es.office.search.ftr.dao.OfficeFileMapper;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.ftr.manager.OfficeFileManager;
import cn.itstar.es.office.search.ftr.service.IndexFileService;
import cn.itstar.es.office.search.ftr.utils.ConstantUtil;
import cn.itstar.es.office.search.ftr.utils.ESUtil;
import cn.itstar.es.office.search.ftr.utils.POIUtil;
import cn.itstar.es.office.search.ftr.utils.PreSubFixUtil;
import cn.itstar.es.office.search.ftr.utils.UpAndDownLoadUtil;
import cn.itstar.es.office.search.shiro.dao.SysRoleDataPrivilegeMapper;
import cn.itstar.es.office.search.shiro.dao.SysUserRoleMapper;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;

@Service("indexFileService")
public class IndexFileServiceImpl implements IndexFileService {

	@Autowired
	private SysRoleDataPrivilegeMapper sysRoleDataPrivilegeMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private OfficeFileManager officeFileManager;
	
	@PostConstruct
	public void init() {
		ElasticSearchUtils.initClient();
	}

	/**
	 * 根据文件级别分页查询所有文件
	 */
	@Override
	public Page<OfficeFileEntity> listIndexFile(Map<String, Object> params) {
		Long userId = (Long) params.get("userId");
		Query form = new Query(params);
		Page<OfficeFileEntity> page = new Page<>(form);
		List<Long> roleIds = sysUserRoleMapper.listUserRoleId(userId);
		//List<SysDataPrivilegeEntity> listDataPrivilegeByUserId = sysRoleDataPrivilegeMapper.listDataPrivilegeByUserId(userId);
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
		
		
		boolean existsIndex = ESUtil.isExistsIndex(ElasticSearchUtils.getESIndex());
		if(existsIndex) {
			page = ESUtil.queryAllByPage(page, ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), null);
		}
		
		return page;
	}
	
	/**
	 * 根据数据库中的文件表中的更新日期按天创建索引
	 */

	@Override
	public R addDBIndexFile() {
		/**
		 * 根据文件修改的日期，查询当天修改过的所有文件信息
		 */
		List<OfficeFileEntity> listOfficeFiles = officeFileManager.listOfficeFileByGmtDayModified();
		for (OfficeFileEntity officeFile : listOfficeFiles) {
			String filePath = officeFile.getFilePath();
			// 获取文件后缀名
			String sufName = filePath.substring(filePath.lastIndexOf(".") + 1);
			
			//通过工具类利用文件名后缀将各种类型的文件内容读取出来
			String officeContent = POIUtil.off2String(new File(filePath), sufName);
			
			officeFile.setContentText(officeContent);
			
			try {
				ESUtil.addIndex(officeFile, ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), officeFile.getFileId().toString());
			} catch (IOException e) {
				return R.error("索引创建失败！");
			}
			
		}
		return null;
	}
	


	/*@Override
	public Page<OfficeFileEntity> addAllIndex(Map<String, Object> params) {
		
		officeFileMapper.getObjectById(fileId);
		
		// 删除所有索引
		ESUtil.deleteAllIndex(ElasticSearchUtils.getESIndex());
		// 根据指定路径初始化所有索引
		ESUtil.addAllIndex(params, allFilePath);

		return null;
	}*/
	
	
	@Override
	public R saveIndexFile(MultipartFile[] uploadFile, OfficeFileEntity officeFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R getIndexFileById(Long indexFileId) {
		OfficeFileEntity officeFile = ESUtil.queryIndexById(ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), indexFileId.toString());
		return CommonUtils.msg(officeFile);
	}

	@Override
	public R updateIndexFile(OfficeFileEntity officeFile) {
		return null;
	}

	@Override
	public R batchRemove(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count = ESUtil.deleteById(ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), id.toString());
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CommonUtils.msg(count);
	}

	@Override
	public R listIndexFilePerms(Long userId) {
		return null;
	}

	@Override
	public R updateIndexFileEnable(Long[] id) {
		return null;
	}

	@Override
	public R updateIndexFileDisable(Long[] id) {
		return null;
	}

	@Override
	public List<SysDataPrivilegeEntity> listLevelTree(Long userId) {
		return null;
	}

	@Override
	public R downloadOfficeFile(Long indexFileId) {
		OfficeFileEntity officeFile = officeFileManager.getById(indexFileId);
		try {
			UpAndDownLoadUtil.DownLoadFile(officeFile.getFilePath());
		} catch (Exception e) {
			return R.error("下载文件失败！");
		}
		return null;
	}

	/**
	 * 检索索引高亮查询
	 */
	@Override
	public Page<OfficeFileEntity> searchIndex(Map<String, Object> params) {
		Long userId = (Long) params.get("userId");
		int pageNo = (int) params.get("curr");
		int pageSize = (int) params.get("limit");
		params.put("pageNumber", pageNo);
		params.put("pageSize", pageSize);
		Query form = new Query(params);
		Page<OfficeFileEntity> page = new Page<>(form);
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
		
		String keyword = (String) params.get("keyword");
		form.put("keyword", keyword);
		
		boolean existsIndex = ESUtil.isExistsIndex(ElasticSearchUtils.getESIndex());
		if(existsIndex) {
			page = ESUtil.queryAllHighlightByPage(page, form, ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType());
		}
		return page;
	}



}
