package cn.itstar.es.office.search.ftr.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import cn.itstar.es.office.search.common.annotation.SysLog;
import cn.itstar.es.office.search.common.constant.SystemConstant;
import cn.itstar.es.office.search.common.controller.AbstractController;
import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.common.utils.ElasticSearchUtils;
import cn.itstar.es.office.search.ftr.dao.OfficeFileMapper;
import cn.itstar.es.office.search.ftr.entity.IKStopEntity;
import cn.itstar.es.office.search.ftr.entity.IKWordEntity;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.ftr.service.IKWordService;
import cn.itstar.es.office.search.ftr.service.IndexFileService;
import cn.itstar.es.office.search.ftr.service.OfficeFileService;
import cn.itstar.es.office.search.ftr.utils.ConstantUtil;
import cn.itstar.es.office.search.ftr.utils.ESUtil;
import cn.itstar.es.office.search.ftr.utils.POIUtil;
import cn.itstar.es.office.search.ftr.utils.PageUtil;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;

/**
 * 索引维护
 * @author star
 *
 */
@Controller
@RestController
@RequestMapping("/sys/index")
public class IndexFileController extends AbstractController  {

	@Autowired
	private IndexFileService indexFileService;
	
	@Autowired
	private OfficeFileService officeFileService;

	
	/**
	 * 索引列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<OfficeFileEntity> list(@RequestBody Map<String, Object> params) {
		if(getUserId() != SystemConstant.SUPER_ADMIN) {
			params.put("userIdCreate", getUserId());
		}
		params.put("userId", getUserId());
		
		return indexFileService.listIndexFile(params);
	}
	
//
/*	*//**
	 * 初始化索引库
	 * 	  会删除所有的索引库后重新建立索引
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping("/initAll.do")
	public Page<OfficeFileEntity> initAll(@RequestBody Map<String, Object> params) {
		
		return indexFileService.addAllIndex(params);
	}*/
	
	/*@RequestMapping("/initAll.do")
	public Page<OfficeFileEntity> initAll(@RequestBody Map<String, Object> params) {
		
		return indexFileService.addAllIndex(params);
	}*/
//	
//	/**
//	 * 在指定目录下创建该目录及子目录下所有文件类型的索引
//	 * @param req
//	 * @param resp
//	 * @return
//	 * @throws Exception 
//	 */
//	@RequestMapping(value = "/addAll.do", method = RequestMethod.POST)
//	public String addAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//
//		Client client = ElasticSearchUtils.getClient();
//		
//		String allFilePath = ConstantUtil.path;
//		OfficeFileEntity officeFile = new OfficeFileEntity();
//		// 获取当前用户
//		SysUserEntity user = getUser();
//		// 设置上传文件的用户
//		officeFile.setUserNameCreate(user.getUsername());
//		// 设置更新文件的用户
//		officeFile.setUserNameUpdate(user.getUsername());
//		
//		indexFileService.addAllIndex(req, resp, officeFile, client, ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), allFilePath);
//		return "redirect:mainTenance.do";
//	}
//	
//
//	/**
//	 * 索引编辑维护
//	 * @param req
//	 * @param resp
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
//	public String toEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		// 获取当前所在页的页号
//		String pageIndex = req.getParameter("curPage");
//		req.setAttribute("curPage", pageIndex);
//		String strId = req.getParameter("id");
//		OfficeFileEntity officeFile = ESUtil.queryIndexById(ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), strId);
//		
//		if (officeFile == null) {
//			return null;
//		}
//		req.setAttribute("officeFile", officeFile);
//		return "admin/indexfile/edit";
//	}
//
//	@RequestMapping(value = "/edit.do", method = RequestMethod.POST)
//	public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		String strId = req.getParameter("id");
//		OfficeFileEntity officeFile = ESUtil.queryIndexById(ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), strId);
//		if (officeFile == null) {
//			return null;
//		}
//		String fileName = req.getParameter("fileName");
//		String filePath = req.getParameter("filePath");
//		String contentText = req.getParameter("contentText");
//		if (fileName == null || "".equals(fileName.trim()) || filePath == null || "".equals(filePath.trim())) {
//			return null;
//		}
//		// 更新
//		officeFile.setFileName(fileName);
//		officeFile.setFilePath(filePath);
//		officeFile.setContentText(contentText.replaceAll("<[^>]*>|\\s", ""));
//		officeFile.setStatus(1);
//		officeFileService.updateOfficeFile(officeFile);
//		ESUtil.updateIndex(officeFile, ElasticSearchUtils.getESIndex(), ElasticSearchUtils.getESType(), officeFile.getFileId().toString());
//
//		return "redirect:list.do";
//	}
	
	
	
	/**
	 * 根据id查询详情
	 * @param officeFileId
	 * @return
	 */
	@RequestMapping("/infoIndex")
	public R getById(@RequestBody Long indexFileId) {
		return indexFileService.getIndexFileById(indexFileId);
	}
	
	/**
	 * 下载文件
	 * @param ids
	 *//*
	@SysLog("下载文件")
	@RequestMapping("/download")
	public R download(@RequestBody Long indexFileId) {
		return indexFileService.downloadOfficeFile(indexFileId);
	}*/
	
	/**
	 * 下载文件
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public R download(HttpServletRequest req, HttpServletResponse resp,@RequestBody OfficeFileEntity officeFile) throws Exception {
		return officeFileService.downLoad(req, resp,officeFile);
	} 
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除索引")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return indexFileService.batchRemove(id);
	}
	
	/**
	 * 启用文件
	 * @param id
	 * @return
	 */
	@SysLog("启用索引")
	@RequestMapping("/enable")
	public R updateOfficeFileEnable(@RequestBody Long[] id) {
		return indexFileService.updateIndexFileEnable(id);
	}
	
	/**
	 * 禁用索引
	 * @param id
	 * @return
	 */
	@SysLog("禁用索引")
	@RequestMapping("/disable")
	public R updateOfficeFileDisable(@RequestBody Long[] ids) {
		return indexFileService.updateIndexFileDisable(ids);
	}
	
	
	/**
	 * 删除全部索引
	 * @return
	 */
	@SysLog("删除全部索引")
	@RequestMapping("/deleteAll")
	public String deleteAll() {
		ESUtil.deleteAllIndex(ElasticSearchUtils.getESIndex());
		return "redirect:list.do";
	}
	
	/**
	 * 检索索引
	 * @param params
	 * @return
	 */
	@SysLog("检索索引")
	@RequestMapping("/search")
	public Page<OfficeFileEntity> searchIndex(@RequestBody Map<String, Object> params) {
		if(getUserId() != SystemConstant.SUPER_ADMIN) {
			params.put("userIdCreate", getUserId());
		}
		params.put("userId", getUserId());
		return indexFileService.searchIndex(params);
	}
	
	/**
	 * 检索索引
	 * @param params
	 * @return
	 */
	@SysLog("检索索引")
	@RequestMapping("/query")
	public Page<OfficeFileEntity> queryIndex(@RequestBody Map<String, Object> params) {
		if(getUserId() != SystemConstant.SUPER_ADMIN) {
			params.put("userIdCreate", getUserId());
		}
		params.put("userId", getUserId());
		return indexFileService.searchIndex(params);
	}
	
}