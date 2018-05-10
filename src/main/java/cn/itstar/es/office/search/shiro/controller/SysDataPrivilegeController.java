package cn.itstar.es.office.search.shiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.itstar.es.office.search.common.annotation.SysLog;
import cn.itstar.es.office.search.common.controller.AbstractController;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;
import cn.itstar.es.office.search.shiro.service.SysDataPrivilegeService;

/**
 * 数据权限
 */
@RestController
@RequestMapping("/sys/data")
public class SysDataPrivilegeController extends AbstractController {

	@Autowired
	private SysDataPrivilegeService sysDataPrivilegeService;
	
	/**
	 * 数据权限列表
	 * @return
	 */
	@RequestMapping("/list")
	public List<SysDataPrivilegeEntity> list() {
		return sysDataPrivilegeService.listDataPrivilege();
	}
	
	/**
	 * 树形数据权限列表，数据权限新增、编辑
	 * @return
	 */
	@RequestMapping("/select")
	public List<SysDataPrivilegeEntity> select() {
		return sysDataPrivilegeService.listDataPrivilegeTree();
	}
	
	/**
	 * 新增数据权限
	 * @param DataPrivilege
	 * @return
	 */
	@SysLog("新增数据权限")
	@RequestMapping("/save")
	public R save(@RequestBody SysDataPrivilegeEntity dataPrivilege) {
		return sysDataPrivilegeService.saveDataPrivilege(dataPrivilege);
	}
	
	/**
	 * 根据id查询数据权限详情
	 * @param DataPrivilegeId
	 * @return
	 */
	@RequestMapping("/info")
	public R info(@RequestBody Long dataPrivilegeId) {
		return sysDataPrivilegeService.getDataPrivilege(dataPrivilegeId);
	}
	
	/**
	 * 修改数据权限
	 * @param DataPrivilege
	 * @return
	 */
	@SysLog("修改数据权限")
	@RequestMapping("/update")
	public R update(@RequestBody SysDataPrivilegeEntity dataPrivilege) {
		return sysDataPrivilegeService.updateDataPrivilege(dataPrivilege);
	}
	
	/**
	 * 删除数据权限
	 * @param id
	 * @return
	 */
	@SysLog("删除数据权限")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return sysDataPrivilegeService.bactchRemoveDataPrivilege(id);
	}
	
}
