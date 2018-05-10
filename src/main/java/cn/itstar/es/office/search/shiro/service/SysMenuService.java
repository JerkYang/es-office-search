package cn.itstar.es.office.search.shiro.service;

import java.util.List;
import java.util.Map;

import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.shiro.entity.SysMenuEntity;

/**
 * 系统菜单
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月10日 上午10:35:58
 */
public interface SysMenuService {
	
	R listUserMenu(Long userId);
	
	List<SysMenuEntity> listMenu(Map<String, Object> params);
	
	R listNotButton();
	
	R saveMenu(SysMenuEntity menu);

	R getMenuById(Long id);
	
	R updateMenu(SysMenuEntity menu);
	
	R batchRemove(Long[] id);

}
