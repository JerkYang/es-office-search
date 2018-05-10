package cn.itstar.es.office.search.shiro.manager;

import java.util.List;
import cn.itstar.es.office.search.shiro.entity.SysOrgEntity;

/**
 * 组织机构
 */
public interface SysOrgManager {

	List<SysOrgEntity> listOrg();
	
	int saveOrg(SysOrgEntity org);
	
	SysOrgEntity getOrg(Long orgId);
	
	int updateOrg(SysOrgEntity org);
	
	int bactchRemoveOrg(Long[] id);
	
	boolean hasChildren(Long[] id);

	List<Long> listOrgChildren(Long parentId);

	List<Long> getAllOrgChildren(Long parentId);
	
}
