package cn.itstar.es.office.search.shiro.security;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.shiro.entity.SysMenuEntity;
import cn.itstar.es.office.search.shiro.manager.SysMenuManager;

/**
 * 产生责任链，确定每个url的访问权限
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年9月5日 下午1:29:36
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysMenuManager sysMenuManager;
	
	// 静态资源访问权限
	private String filterChainDefinitions = null;

	public Ini.Section getObject() throws Exception {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		List<SysMenuEntity> lists = sysMenuManager.listMenu(new Query());
		for(SysMenuEntity menu : lists) {
			String permKey = menu.getPerms();
			String permUrl = menu.getUrl();
			if(StringUtils.isNotEmpty(permKey) && StringUtils.isNotEmpty(permUrl)) {
				String permission = "perms[" + permKey + "]";
				section.put(permUrl + "", permission);
				log.info("初始化权限:{}={}", permUrl, permission);
			}
		}
		// 所有资源的访问权限，必须放在最后
		section.put("/**", "authc");
		return section;
	}

	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * @param filterChainDefinitions
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}
}
