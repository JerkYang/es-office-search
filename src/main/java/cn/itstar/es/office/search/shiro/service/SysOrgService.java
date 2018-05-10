package cn.itstar.es.office.search.shiro.service;

import java.util.List;

import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.shiro.entity.SysOrgEntity;

/**
 * 组织机构
 */
public interface SysOrgService {

	List<SysOrgEntity> listOrg();
	
	List<SysOrgEntity> listOrgTree();
	
	R saveOrg(SysOrgEntity org);
	
	R getOrg(Long orgId);
	
	R updateOrg(SysOrgEntity org);
	
	R bactchRemoveOrg(Long[] id);
	
}
