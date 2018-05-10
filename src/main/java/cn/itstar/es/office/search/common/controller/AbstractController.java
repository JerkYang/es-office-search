package cn.itstar.es.office.search.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.common.utils.ShiroUtils;

/**
 * Controller公共组件
 */
public abstract class AbstractController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
	
}
