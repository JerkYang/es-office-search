package cn.itstar.es.office.search.ftr.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.shiro.entity.SysDataPrivilegeEntity;

/**
 * 系统文件dao
 */
@MapperScan
public interface OfficeFileMapper extends BaseMapper<OfficeFileEntity> {

	OfficeFileEntity getByOfficeFileName(String officeFileName);
	
	int updateOfficeFileStatus(Query query);

	List<OfficeFileEntity> listForPage();

	/**
	 * 根据文件级别分页查询
	 * @param page
	 * @param search
	 * @return
	 */
	List<OfficeFileEntity> listForPageByFileLevel(Page<OfficeFileEntity> page, Query search);

	/**
	 * 根据文件修改日期，查询出当前修改过的所有文件信息
	 * @return
	 */
	List<OfficeFileEntity> listOfficeFileByGmtDayModified();

}
