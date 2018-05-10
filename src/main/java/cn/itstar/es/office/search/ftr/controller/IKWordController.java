package cn.itstar.es.office.search.ftr.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.itstar.es.office.search.common.annotation.SysLog;
import cn.itstar.es.office.search.common.constant.SystemConstant;
import cn.itstar.es.office.search.common.controller.AbstractController;
import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.ftr.entity.IKWordEntity;
import cn.itstar.es.office.search.ftr.service.IKWordService;

/**
 * 系统索引拓展词
 */
@RestController
@RequestMapping("/sys/ikword")
public class IKWordController extends AbstractController {
	
	@Autowired
	private IKWordService ikWordService;
	
	/**
	 * 拓展词列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<IKWordEntity> list(@RequestBody Map<String, Object> params) {
		if(getUserId() != SystemConstant.SUPER_ADMIN) {
			params.put("userIdCreate", getUserId());
		}
		return ikWordService.listIKWord(params);
	}
	
	/**
	 * 拓展词权限
	 * @return
	 */
	@RequestMapping("/perms")
	public R listUserPerms() {
		return ikWordService.listIKWordPerms(getUserId());
	}
	
	/**
	 * 新增拓展词
	 * @param ikWord
	 * @return
	 */
	@SysLog("新增拓展词")
	@RequestMapping("/save")
	public R save(@RequestBody IKWordEntity ikWord) {
		ikWord.setUserIdCreate(getUserId());
		return ikWordService.saveIKWord(ikWord);
	}
	
	/**
	 * 根据id查询详情
	 * @param ikWordId
	 * @return
	 */
	@RequestMapping("/infoIKWord")
	public R getById(@RequestBody Long ikWordId) {
		return ikWordService.getIKWordById(ikWordId);
	}
	
	/**
	 * 修改拓展词
	 * @param user
	 * @return
	 */
	@SysLog("修改拓展词")
	@RequestMapping("/update")
	public R update(@RequestBody IKWordEntity ikWord) {
		return ikWordService.updateIKWord(ikWord);
	}
	
	/**
	 * 删除拓展词
	 * @param id
	 * @return
	 */
	@SysLog("删除拓展词")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return ikWordService.batchRemove(id);
	}
	/**
	 * 启用拓展词
	 * @param id
	 * @return
	 */
	@SysLog("启用拓展词")
	@RequestMapping("/enable")
	public R updateUserEnable(@RequestBody Long[] id) {
		return ikWordService.updateIKWordEnable(id);
	}
	
	/**
	 * 禁用拓展词
	 * @param id
	 * @return
	 */
	@SysLog("禁用拓展词")
	@RequestMapping("/disable")
	public R updateUserDisable(@RequestBody Long[] id) {
		return ikWordService.updateIKWordDisable(id);
	}
}
