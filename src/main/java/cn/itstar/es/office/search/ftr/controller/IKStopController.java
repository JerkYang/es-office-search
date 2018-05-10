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
import cn.itstar.es.office.search.ftr.entity.IKStopEntity;
import cn.itstar.es.office.search.ftr.service.IKStopService;

/**
 * 系统索引停用词
 */
@RestController
@RequestMapping("/sys/ikstop")
public class IKStopController extends AbstractController {
	
	@Autowired
	private IKStopService ikStopService;
	
	/**
	 * 停用词列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<IKStopEntity> list(@RequestBody Map<String, Object> params) {
		if(getUserId() != SystemConstant.SUPER_ADMIN) {
			params.put("userIdCreate", getUserId());
		}
		return ikStopService.listIKStop(params);
	}
	
	/**
	 * 停用词权限
	 * @return
	 */
	@RequestMapping("/perms")
	public R listUserPerms() {
		return ikStopService.listIKStopPerms(getUserId());
	}
	
	/**
	 * 新增停用词
	 * @param ikStop
	 * @return
	 */
	@SysLog("新增停用词")
	@RequestMapping("/save")
	public R save(@RequestBody IKStopEntity ikStop) {
		ikStop.setUserIdCreate(getUserId());
		return ikStopService.saveIKStop(ikStop);
	}
	
	/**
	 * 根据id查询详情
	 * @param ikStopId
	 * @return
	 */
	@RequestMapping("/infoIKStop")
	public R getById(@RequestBody Long ikStopId) {
		return ikStopService.getIKStopById(ikStopId);
	}
	
	/**
	 * 修改停用词
	 * @param user
	 * @return
	 */
	@SysLog("修改停用词")
	@RequestMapping("/update")
	public R update(@RequestBody IKStopEntity ikStop) {
		return ikStopService.updateIKStop(ikStop);
	}
	
	/**
	 * 删除停用词
	 * @param id
	 * @return
	 */
	@SysLog("删除停用词")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return ikStopService.batchRemove(id);
	}
	/**
	 * 启用停用词
	 * @param id
	 * @return
	 */
	@SysLog("启用停用词")
	@RequestMapping("/enable")
	public R updateUserEnable(@RequestBody Long[] id) {
		return ikStopService.updateIKStopEnable(id);
	}
	
	/**
	 * 禁用停用词
	 * @param id
	 * @return
	 */
	@SysLog("禁用停用词")
	@RequestMapping("/disable")
	public R updateUserDisable(@RequestBody Long[] id) {
		return ikStopService.updateIKStopDisable(id);
	}
}
