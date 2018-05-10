package cn.itstar.es.office.search.ftr.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.itstar.es.office.search.common.annotation.SysLog;
import cn.itstar.es.office.search.common.constant.SystemConstant;
import cn.itstar.es.office.search.common.controller.AbstractController;
import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.ftr.manager.OfficeFileManager;
import cn.itstar.es.office.search.ftr.service.OfficeFileService;
import cn.itstar.es.office.search.ftr.utils.PreSubFixUtil;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;
import cn.itstar.es.office.search.shiro.service.SysDataPrivilegeService;
import cn.itstar.es.office.search.shiro.service.SysUserService;

@Controller
@RestController
@RequestMapping("/sys/offices")
public class OfficeFileController extends AbstractController {
	
	@Autowired
	private OfficeFileService officeFileService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDataPrivilegeService sysDataPrivilegeService;
	@Autowired
	private OfficeFileManager officeFileManager;
	
	
	/**
	 * 文件列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<OfficeFileEntity> list(@RequestBody Map<String, Object> params) {
		if(getUserId() != SystemConstant.SUPER_ADMIN) {
			params.put("userIdCreate", getUserId());
		}
		params.put("userId", getUserId());
		return officeFileService.listOfficeFile(params);
	}
	
	/**
	 * 查询角色所拥有的文件级别
	 * @return
	 */
	@RequestMapping(value = "/select")
	public List<SysDataPrivilegeEntity> listLevel() {
		return officeFileService.listLevelTree(getUserId());
	}
	
	/**
	 * 新增文件
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	@SysLog("新增文件")
	@RequestMapping(value = "/save" , produces="application/json;charset=UTF-8")
//	public R save(@RequestParam MultipartFile[] uploadFile,@RequestBody OfficeFileEntity file,HttpServletResponse rsp) {
	public R save(@RequestParam("file") MultipartFile[] uploadFile,OfficeFileEntity officeFile) {
		
		officeFile.setUserIdCreate(getUserId());
		officeFile.setUserIdUpdate(getUserId());
		return officeFileService.saveOfficeFile(uploadFile, officeFile);
	}
	
	/**
	 * 根据id查询详情
	 * @param officeFileId
	 * @return
	 */
	@RequestMapping("/infoFile")
	public R getById(@RequestBody Long officeFileId) {
		return officeFileService.getOfficeFileById(officeFileId);
	}
	
	/**
	 * 修改文件
	 * @param officeFile
	 * @return
	 */
	@SysLog("修改文件")
	@RequestMapping("/update")
	public R update(@RequestBody OfficeFileEntity officeFile) {
		return officeFileService.updateOfficeFile(officeFile);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除文件")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return officeFileService.batchRemove(id);
	}
	
	/**
	 * 启用文件
	 * @param id
	 * @return
	 */
	@SysLog("启用文件")
	@RequestMapping("/enable")
	public R updateOfficeFileEnable(@RequestBody Long[] id) {
		return officeFileService.updateOfficeFileEnable(id);
	}
	
	/**
	 * 禁用文件
	 * @param id
	 * @return
	 */
	@SysLog("禁用文件")
	@RequestMapping("/disable")
	public R updateOfficeFileDisable(@RequestBody Long[] ids) {
		return officeFileService.updateOfficeFileDisable(ids);
	}
	
	
	/*@SysLog("下载文件")
	@RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
    		Long[] ids,
            Model model)throws Exception {
    	
    	for (Long id : ids) {
    		OfficeFileEntity officeFile = officeFileManager.getById(id);
    		String filename = officeFile.getFileName();
    		String filepath = officeFile.getFilePath();
    		String fileNameWithSub = PreSubFixUtil.getFileNameWithSub(filepath);
    		//下载文件路径
    		String path = request.getServletContext().getRealPath("/upload/");
    		File file = new File(path + File.separator + fileNameWithSub);
    		HttpHeaders headers = new HttpHeaders();  
    		//下载显示的文件名，解决中文名称乱码问题  
    		String downloadFielName = new String(fileNameWithSub.getBytes("UTF-8"),"iso-8859-1");
    		//通知浏览器以attachment（下载方式）打开图片
    		headers.setContentDispositionFormData("attachment", downloadFielName); 
    		//application/octet-stream ： 二进制流数据（最常见的文件下载）。
    		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
    				headers, HttpStatus.CREATED);  
		}
		return null;
    }*/
	
	
	/**
	 * 下载文件
	 * @param ids
	 */
	@SysLog("下载文件")
	@RequestMapping("/download")
	public R download(@RequestParam Long[] ids) {
		/*String[] strIds = ids.split(",");
		Long[] fileIds = new Long[strIds.length];
		 for (int i = 0; i < strIds.length; i++) {
			 fileIds[i] = Long.parseLong(strIds[i]);
		 }
		 ids = null;*/
		return officeFileService.downloadOfficeFile(ids);
	}
	
	/**
	 * 下载文件
	 * @param ids
	 */
	@SysLog("单一文件下载")
	@RequestMapping("/onedown")
	public R download(@RequestParam("id") Long id) {
		//long fileId = Long.parseLong(id);
		return officeFileService.downloadOneOfficeFile(id);
	}
/*	
	@RequestMapping(value="/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String filename, Model model)throws Exception {
       //下载文件路径
       String path = request.getServletContext().getRealPath("/images/");
       File file = new File(path + File.separator + filename);
       HttpHeaders headers = new HttpHeaders();  
       //下载显示的文件名，解决中文名称乱码问题  
       String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
       //通知浏览器以attachment（下载方式）打开图片
       headers.setContentDispositionFormData("attachment", downloadFielName); 
       //application/octet-stream ： 二进制流数据（最常见的文件下载）。
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
               headers, HttpStatus.CREATED);  
    }*/
}
